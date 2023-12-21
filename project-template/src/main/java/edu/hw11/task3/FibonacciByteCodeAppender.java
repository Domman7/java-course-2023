package edu.hw11.task3;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.jetbrains.annotations.NotNull;

public class FibonacciByteCodeAppender implements ByteCodeAppender {
    @Override
    public @NotNull Size apply(
        MethodVisitor methodVisitor,
        Implementation.@NotNull Context context,
        @NotNull MethodDescription instrumentedMethod
    ) {
        methodVisitor.visitCode();

        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitInsn(Opcodes.ICONST_0);
        Label label = new Label();

        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPEQ, label);
        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitInsn(Opcodes.ICONST_1);

        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPEQ, label);
        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitInsn(Opcodes.ICONST_1);

        methodVisitor.visitInsn(Opcodes.ISUB);
        methodVisitor.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            instrumentedMethod.getDeclaringType().asErasure().getName(),
            instrumentedMethod.getName(),
            instrumentedMethod.getDescriptor(),
            false
        );

        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitInsn(Opcodes.ICONST_2);
        methodVisitor.visitInsn(Opcodes.ISUB);

        methodVisitor.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            instrumentedMethod.getDeclaringType().asErasure().getName(),
            instrumentedMethod.getName(),
            instrumentedMethod.getDescriptor(),
            false
        );

        methodVisitor.visitInsn(Opcodes.IADD);
        methodVisitor.visitInsn(Opcodes.IRETURN);
        methodVisitor.visitLabel(label);

        methodVisitor.visitInsn(Opcodes.LCONST_1);
        methodVisitor.visitInsn(Opcodes.IRETURN);
        methodVisitor.visitMaxs(0, 0);

        methodVisitor.visitEnd();

        return new Size(instrumentedMethod.getStackSize(), instrumentedMethod.getStackSize());
    }
}
