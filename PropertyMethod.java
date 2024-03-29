/* PropertyMethod.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * Represents a single property method
 */

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.IntStream;

public class PropertyMethod {
    private Method m;
    private Class<?> c;
    private Object o;
    
    public String getName() {
        return m.getName();
    }

    public PropertyMethod(Method m, Class<?> c, Object o) {
        this.m = m;
        this.c = c;
        this.o = o;
    }

    /* run
     *    Purpose: Generate the possible parameters for and run a provided method
     * Parameters: A method to run
     *    Returns: The parameter list that caused the test to fail, or null
     *    Effects: none
     */
    public Object[] run() {
        List<Object[]> params = generateParams();

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


    /* generateParams
     *    Purpose: Produces the possible argument lists given a list of
     *             annotated parameters with ranges and stuff
     * Parameters: An array of parameters, each annotated appropriately
     *    Returns: A list containing all possible argument lists given the
     *             parameters.
     *    Effects: none
     */
    private List<Object[]> generateParams() {
        List<Object[]> gen_plists = new ArrayList<>();
        List<List<Object>> arg_lists = new ArrayList<>();

        for (Parameter p : getParams()) {
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

    /* processParam
     *    Purpose: Genrates all possible versions of the input type
     * Parameters: A parameter annotated with one of the @Property annotations
     *    Returns: A list of all possible versions of the parameter
     *    Effects: none
     */
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

    /* processInteger
     *    Purpose: Generates a list of possible Integers
     * Parameters: A type annotated with @IntRange
     *    Returns: The list of Integers in that range.
     *    Effects: none
     */
    private List<Integer> processInteger(AnnotatedType p) {
        IntRange ir = p.getAnnotation(IntRange.class);
        return IntStream.rangeClosed(ir.min(), ir.max()).boxed().toList();
    }

    /* processString
     *    Purpose: Generates a list of possible Strings
     * Parameters: A type annotated with @StringSet
     *    Returns: The list of Strings in that annotation
     *    Effects: none
     */
    private List<String> processString(AnnotatedType p) {
        return List.of(p.getAnnotation(StringSet.class).strings());
    }

    /* processObject
     *    Purpose: Generates a list of Objects by calling the appropriate method
     *             the specified number of times.
     * Parameters: A type annotated with @ForAll
     *    Returns: A list of Objects generated by the method named in the
     *             annotation
     */
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

    /* processList
     *    Purpose: Generates all possible lists specified by the annotated type.
     * Parameters: A parametized type annotated with @ListLength
     *    Returns: The list of possible lists of the appropriate length and type
     *    Effects: none
     */
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
    /* notDoneYet
     *    Purpose: A helper for the variable-modulus counting we need to do
     *             that checks if we have reached the end of the permutations
     * Parameters: An array of integers that represents a variable-modulo number
     *    Returns: true if any of the ints are not 0, false otherwise
     *    Effects: none
     */
    private boolean notDoneYet(int []pos) {
        for (int i : pos) {
            if (i != 0) {
                return true;
            }
        }
        return false;
    }

    /* updatePos
     *    Purpose: Increments the number represented by the first argument in
     *             the second argument's modulo
     * Parameters: An array of integers representing a number mod the second argument 
     *    Returns: none
     *    Effects: Increments the first argument
     */
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

    /* updatePos
     *    Purpose: Increments the number represented by the first argument in
     *             the second argument's variable modulo
     * Parameters: An array of integers representing a number mod the second argument 
     *    Returns: none
     *    Effects: Increments the first argument
     */
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

    /* getParams
     *    Purpose: Gets the parameters of a method and validates them
     * Parameters: A method to get the parameters of
     *    Returns: The parameters
     *    Effects: throws if a param is invalid
     */
    private Parameter[] getParams() {
        Parameter params[] = m.getParameters();

        for (Parameter p : params) {
            assertValidParam(p);
        }

        return params;
    }

    /* assertValidParam
     *    Purpose: Makes sure that a parameter is of the right type and is
     *             annotated correctly
     * Parameters: A parameter to validate
     *    Returns: none
     *    Effects: throws if the param is invalid
     */
    private void assertValidParam(Parameter p) {
        Class<?> cl = p.getType();

        if (!((Integer.class.isAssignableFrom(cl) && p.getAnnotation(IntRange.class)   != null) 
           || (String.class.isAssignableFrom(cl)  && p.getAnnotation(StringSet.class)  != null) 
           || (List.class.isAssignableFrom(cl)    && p.getAnnotation(ListLength.class) != null) 
           || (Object.class.isAssignableFrom(cl)  && p.getAnnotation(ForAll.class)     != null))) {
            throw new IncompatibleAnnotationsException();
        }
    }
}
