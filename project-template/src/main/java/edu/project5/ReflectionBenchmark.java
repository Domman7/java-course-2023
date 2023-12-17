package edu.project5;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Thread)
public class ReflectionBenchmark {
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(ReflectionBenchmark.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .forks(1)
            .warmupForks(1)
            .warmupIterations(1)
            .warmupTime(TimeValue.seconds(5))
            .measurementIterations(1)
            .measurementTime(TimeValue.seconds(120))
            .build();

        new Runner(options).run();
    }

    record Student(String name, String surname) {
    }

    private Student student;
    private Method method;
    private MethodHandle methodHandle;
    private StudentAccessor studentAccessor;

    @Setup
    public void setup() throws Throwable {
        student = new Student("Alexander", "Biryukov");
        method = Student.class.getMethod("name");
        methodHandle = MethodHandles.lookup().findVirtual(Student.class, "name", MethodType.methodType(String.class));
        studentAccessor = createStudentAccessor();
    }

    @Benchmark
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    @Benchmark
    public void reflection(Blackhole bh) throws InvocationTargetException, IllegalAccessException {
        String name = (String) method.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    public void methodHandle(Blackhole bh) throws Throwable {
        String name = (String) methodHandle.invokeExact(student);
        bh.consume(name);
    }

    @Benchmark
    public void lambdaMetafactory(Blackhole bh) {
        String name = studentAccessor.name(student);
        bh.consume(name);
    }

    private StudentAccessor createStudentAccessor() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(String.class, Student.class);
        MethodHandle target = lookup.findVirtual(Student.class, "name", MethodType.methodType(String.class));
        MethodType accessorMethodType = MethodType.methodType(String.class, Object.class);

        return (StudentAccessor) LambdaMetafactory.metafactory(
                lookup,
                "name",
                MethodType.methodType(StudentAccessor.class),
                accessorMethodType,
                target,
                methodType
            )
            .getTarget()
            .invokeExact();
    }

    interface StudentAccessor {
        String name(Object student);
    }
}
