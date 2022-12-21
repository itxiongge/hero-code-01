package com.hero.jvm.classloader;

import java.io.*;

public class HeroClassLoader extends ClassLoader {

    private String classpath;

    public HeroClassLoader(String classpath) {
        this.classpath = classpath;
    }

    /**
     * 类加载器作用：加载class文件
     * 入参：类全限定名称，定位class文件
     * 出参：Class对象
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            //输入流，通过类的全限定名称加载文件到字节数组
            byte[] classDate = getData(name);
            if (classDate != null) {
                //defineClass方法将字节数组数据 转为 字节码对象
                return defineClass(name, classDate, 0, classDate.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.findClass(name);
    }

    //加载类的字节码数据
    private byte[] getData(String className) throws IOException {
        //根据className生成Class对应的路径
        String path = classpath + File.separatorChar +
                className.replace('.', File.separatorChar) + ".class";
        try (InputStream in = new FileInputStream(path);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return out.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
