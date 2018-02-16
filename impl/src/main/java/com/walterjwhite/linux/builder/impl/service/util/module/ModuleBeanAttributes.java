package com.walterjwhite.linux.builder.impl.service.util.module;

// public class ModuleBeanAttributes implements BeanAttributes<Module> {
//    protected final Class<? extends Module> moduleClass;
//
//    protected final Set<Type> types;
//    protected final Set<Annotation> qualifiers;
//
//    public ModuleBeanAttributes(Class<? extends Module> moduleClass) {
//        super();
//        this.moduleClass = moduleClass;
//
//        types = new HashSet<>();
//        qualifiers = new HashSet<>();
//
//        types.add(moduleClass);
//
//        for(Annotation annotation:moduleClass.getAnnotations()) {
//            if(annotation.annotationType().isAssignableFrom(Qualifier.class)) {
//                qualifiers.add(annotation);
//            }
//        }
//    }
//
//    @Override
//    public Set<Type> getTypes() {
//        return(types);
//    }
//
//    @Override
//    public Set<Annotation> getQualifiers() {
//        return(qualifiers);
//    }
//
//    @Override
//    public Class<? extends Annotation> getScope() {
//        return(Dependent.class);
//    }
//
//    @Override
//    public String getName() {
//        return(moduleClass.getName());
//    }
//
//    @Override
//    public Set<Class<? extends Annotation>> getStereotypes() {
//        return new HashSet<>();
//    }
//
//    @Override
//    public boolean isAlternative() {
//        return false;
//    }
// }
