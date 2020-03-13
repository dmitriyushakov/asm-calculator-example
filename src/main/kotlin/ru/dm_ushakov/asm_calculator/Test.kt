package ru.dm_ushakov.asm_calculator

import org.objectweb.asm.*
import org.objectweb.asm.Opcodes.*

fun makeCalculatorClass() = ClassWriter(0).apply {
    visit(V1_8, ACC_PUBLIC,"ru/dm_ushakov/asm_calculator/Calculator",null,"java/lang/Object",null)
    visitMethod(ACC_PUBLIC + ACC_STATIC,"run","()V",null,null).apply {
        visitCode()

        visitFieldInsn(GETSTATIC,"java/lang/System","out","Ljava/io/PrintStream;")
        visitInsn(DUP)
        visitLdcInsn("Please input two integer numbers:")
        visitMethodInsn(INVOKEVIRTUAL,"java/io/PrintStream","println","(Ljava/lang/String;)V",false)
        visitTypeInsn(NEW,"java/util/Scanner")
        visitInsn(DUP)
        visitFieldInsn(GETSTATIC,"java/lang/System","in","Ljava/io/InputStream;")
        visitMethodInsn(INVOKESPECIAL,"java/util/Scanner","<init>","(Ljava/io/InputStream;)V",false)
        visitInsn(DUP)
        visitVarInsn(ASTORE,0)
        visitMethodInsn(INVOKEVIRTUAL,"java/util/Scanner","nextInt","()I",false)
        visitVarInsn(ALOAD,0)
        visitMethodInsn(INVOKEVIRTUAL,"java/util/Scanner","nextInt","()I",false)
        visitInsn(IADD)
        visitVarInsn(ISTORE,1)
        visitInsn(DUP)
        visitLdcInsn("Sum of numbers - ")
        visitMethodInsn(INVOKEVIRTUAL,"java/io/PrintStream","print","(Ljava/lang/String;)V",false)
        visitVarInsn(ILOAD,1)
        visitMethodInsn(INVOKEVIRTUAL,"java/io/PrintStream","println","(I)V",false)
        visitInsn(RETURN)
        visitMaxs(4,2)

        visitEnd()
    }
    visitEnd()
}.toByteArray()

class AsmClassLoader : ClassLoader() {
    fun defineClass(name:String, bytes:ByteArray) = defineClass(name,bytes,0,bytes.size)
}

fun main() {
    val bytecode = makeCalculatorClass()
    val cl = AsmClassLoader()
    val calculatorClass = cl.defineClass("ru.dm_ushakov.asm_calculator.Calculator",bytecode)

    calculatorClass.getMethod("run").invoke(null)
}