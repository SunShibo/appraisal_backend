package com.wisewin.api.support.typehandler;

import com.wisewin.api.support.IntEnum;
import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.asm.*;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * UserBO: tgic
 * Date: 4/14/13
 * Time: 2:12 PM
 */
public class IntEnumTypeHandlersFactoryBean implements FactoryBean<List<TypeHandler<? extends IntEnum>>>, ResourceLoaderAware, InitializingBean {

    private final String ENUM_CLASSNAME = Enum.class.getName();
    private final String INTENUM_CLASSNAME = IntEnum.class.getName();


    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
    private ResourceLoader resourceLoader;
    private String basePackage;
    private List<TypeHandler<? extends IntEnum>> typeHandlers;

    @Override
    public List<TypeHandler<? extends IntEnum>> getObject() throws Exception {

        if (typeHandlers == null) {
            afterPropertiesSet();
        }

        return typeHandlers;
    }

    @SuppressWarnings("unchecked")
	private TypeHandler<? extends IntEnum> buildTypeHandler(Class<?> enumClazz) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        final String clzName = enumClazz.getName() + "$TypeHandler";

        cw.visit(Opcodes.V1_6, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, clzName.replace('.', '/'),
                null, Type.getInternalName(IntEnumTypeHandler.class),
                null);

        AnnotationVisitor av = cw.visitAnnotation(Type.getDescriptor(MappedTypes.class), true);
        AnnotationVisitor av1 = av.visitArray("value");
        av1.visit(null, Type.getType(enumClazz));
        av1.visitEnd();
        av.visitEnd();

        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>",
                Type.getMethodDescriptor(Type.VOID_TYPE, new Type[]{Type.getType(Class.class)} ), null, null);

        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL,
                Type.getInternalName(IntEnumTypeHandler.class), "<init>",
                Type.getMethodDescriptor(Type.VOID_TYPE, new Type[]{Type.getType(Class.class)}));
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        cw.visitEnd();


        Class<?> clz = (new ClassLoader(Thread.currentThread().getContextClassLoader()) {
            Class<?> make(byte[] b) {
                return defineClass(clzName, b, 0, b.length);
            }
        }).make(cw.toByteArray());

        Constructor<?> constructor = clz.getConstructor(Class.class);

        return (TypeHandler<? extends IntEnum>) constructor.newInstance(enumClazz);
    }

    @Override
    public Class<?> getObjectType() {
        return List.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    protected String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(basePackage);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ArrayList<TypeHandler<? extends IntEnum>> typeHandlers = new ArrayList<TypeHandler<? extends IntEnum>>();

        resourcePatternResolver = new PathMatchingResourcePatternResolver(resourceLoader);
        metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);

        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                resolveBasePackage(basePackage) + "/**/*.class";
        Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
        for (Resource resource : resources) {
            if (resource.isReadable()) {
                try {
                    MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
                    ClassMetadata classMetadata = metadataReader.getClassMetadata();

                    if (ENUM_CLASSNAME.equals(classMetadata.getSuperClassName()) &&
                            ArrayUtils.contains(classMetadata.getInterfaceNames(), INTENUM_CLASSNAME)) {

                        Class<?> clz = Class.forName(classMetadata.getClassName());

                        typeHandlers.add(buildTypeHandler(clz));
                    }

                } catch (Throwable ex) {
                }
            }
        }

        this.typeHandlers = Collections.unmodifiableList(typeHandlers);
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}
