/* PropertyMethods.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * A class that parses, maintains, and runs the Property methods of the class
 * that we are trying to test.
 */

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.IntStream;
import java.lang.annotation.Annotation;

public class PropertyMethods {
    private List<Method> meths = new ArrayList<>();
    private Class<?> c;
    private Object o;

    public PropertyMethods(Class<?> c) {
        this.c = c;
        o = instantiateClass(c);

        Method all_methods[] = c.getMethods();
            for (Method m : all_methods) {
                Annotation ans[] = m.getAnnotations();
                for (Annotation a : ans) {
                    if (a instanceof Property) {
                        meths.add(m);
                        break;
                    }
                }
            }
        Collections.sort(meths, (o1, o2) -> o1.getName().compareTo(o2.getName()));
    }

    public Map<String, Object[]> run() {
        Map<String, Object[]> results = new HashMap<>();

        for (Method m : meths) {
            Object[] result = process(m);
            results.put(m.getName(), result);
        }

        return results;
    }

    public String toString() {
        return String.format("PropertyMethods: %s", meths.toString());
    }

    private Object[] process(Method m) {
        Parameter raw_params[] = getParams(m);
        List<Object[]> params = generateParams(raw_params);

        int len = params.size() < 100 ? params.size() : 100;
        for (int i = 0; i < len; i++) {
            Object[] args = params.get(i);
            try {
                Object result = m.invoke(o, args);
                if ((result instanceof Boolean) && !(Boolean)result) {
                    return args;
                }
            } catch (IllegalAccessException ex) {
                throw new BadMethodException();
            } catch (InvocationTargetException ex) {
                throw new BadMethodException();
            } catch (Throwable ex) {
                return args;
            }
        }

        return null; 
    }

    private List<Object[]> generateParams(Parameter []params) {
        List<Object[]> gen_plists = new ArrayList<>();
        List<List<Object>> arg_lists = new ArrayList<>();
        for (Parameter p : params) {
            arg_lists.add(processParam(p.getAnnotatedType()));
        }

        int num_args = arg_lists.size();
        int []pos = new int[num_args];
        int []lens = new int[num_args];

        for (int i = 0; i < num_args; i++) {
            pos[i] = 0;
            lens[i] = arg_lists.get(i).size();
        }

        do {
            Object []args = new Object[num_args];
            for (int i = 0; i < num_args; i++) {
                args[i] = arg_lists.get(i).get(pos[i]);
            }

            gen_plists.add(args);

            updatePos(pos, lens);
            
        } while (notDoneYet(pos));

        return gen_plists;

    }

    private List<Object> processParam(AnnotatedType p) {
        if (p.getAnnotation(IntRange.class) != null) {
            List<Integer> ints = processInteger(p);
            return new ArrayList<Object>(ints);
        } else if (p.getAnnotation(StringSet.class) != null) {
            List<String> strings = processString(p);
            return new ArrayList<Object>(strings);
        } else if (p.getAnnotation(ListLength.class) != null) {
            if (!(p instanceof AnnotatedParameterizedType)) {
                throw new IncompatibleParametersException();
            }
            AnnotatedParameterizedType ap = (AnnotatedParameterizedType)p;
            List<List<Object>> lists = processList(ap);
            return new ArrayList<Object>(lists);
        } else {
            return processObject(p);
        }
    }

    private List<Integer> processInteger(AnnotatedType p) {
        IntRange ir = p.getAnnotation(IntRange.class);
        return IntStream.rangeClosed(ir.min(), ir.max()).boxed().toList();
    }

    private List<String> processString(AnnotatedType p) {
        return List.of(p.getAnnotation(StringSet.class).strings());
    }

    private List<List<Object>> processList(AnnotatedParameterizedType p) {
        ListLength anot = p.getAnnotation(ListLength.class);
        List<List<Object>> out_list = new ArrayList<>();
        List<Object> sub_list = processParam(p.getAnnotatedActualTypeArguments()[0]);
        int max = sub_list.size();

        for (int len = anot.min(); len <= anot.max(); len++) {
            int []pos = new int[len];
            for (int i = 0; i < len; i++) {
                pos[i] = 0;
            }
            do {
                List<Object> l = new ArrayList<>(len);
                for (int i = 0; i < len; i++) {
                    l.add(sub_list.get(pos[i]));
                }
                out_list.add(l);
                updatePos(pos, max);
            } while (notDoneYet(pos));

        }

        return out_list;
    }

    private boolean notDoneYet(int []pos) {
        for (int i : pos) {
            if (i != 0) {
                return true;
            }
        }
        return false;
    }

    private void updatePos(int []pos, int size) {
        for (int i = 0; i < pos.length; i++) {
            pos[i]++;
            if (pos[i] == size) {
                pos[i] = 0;
            } else {
                return;
            }
        }
    }

    private void updatePos(int []pos, int []lens) {
        for (int i = 0; i < pos.length; i++) {
            pos[i]++;
            if (pos[i] == lens[i]) {
                pos[i] = 0;
            } else {
                return;
            }
        }
    }

    private List<Object> processObject(AnnotatedType p) {
        ForAll f = p.getAnnotation(ForAll.class);

        int times = f.times();
        List<Object> objs = new ArrayList<>(times);

        try {
            Method m = c.getMethod(f.name());

            for (int i = 0; i < times; i++) {
                objs.add(m.invoke(o, (Object[])null));
            }

        } catch (NoSuchMethodException ex) {
            throw new IncompatibleParametersException();
        } catch (IllegalAccessException ex) {
            throw new IncompatibleParametersException();
        } catch (InvocationTargetException ex) {
            throw new IncompatibleParametersException();
        }

        return objs;
    }

    private void assertValidParam(Parameter p) {
        Class<?> cl = p.getType();

        if (!((Integer.class.isAssignableFrom(cl) && p.getAnnotation(IntRange.class)   != null) 
           || (String.class.isAssignableFrom(cl)  && p.getAnnotation(StringSet.class)  != null) 
           || (List.class.isAssignableFrom(cl)    && p.getAnnotation(ListLength.class) != null) 
           || (Object.class.isAssignableFrom(cl)  && p.getAnnotation(ForAll.class)     != null))) {
            throw new IncompatibleAnnotationsException();
        }
    }

    private Parameter[] getParams(Method m) {
        Parameter params[] = m.getParameters();

        for (Parameter p : params) {
            assertValidParam(p);
        }

        return params;
    }

    private Object instantiateClass(Class<?> c) {
        try {
            return c.getConstructor().newInstance();
        } catch (NoSuchMethodException ex) {
            throw new BadClassException();
        } catch (IllegalAccessException ex) {
            throw new BadClassException();
        } catch (InvocationTargetException ex) {
            throw new BadClassException();
        } catch (InstantiationException ex) {
            throw new BadClassException();
        }
    }
}
