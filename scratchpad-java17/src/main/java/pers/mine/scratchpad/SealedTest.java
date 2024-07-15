package pers.mine.scratchpad;


public class SealedTest {
    // 添加sealed修饰符，permits后面跟上只能被继承的子类名称
    public sealed class Person permits Teacher, Worker, Student{ } //人

    // 子类可以被修饰为 final
    final class Teacher extends Person { }//教师

    // 子类可以被修饰为 non-sealed，此时 Worker类就成了普通类，谁都可以继承它
    non-sealed class Worker extends Person { }  //工人
    // 任何类都可以继承Worker
    class AnyClass extends Worker{}

    //子类可以被修饰为 sealed,同上
    sealed class Student extends Person permits MiddleSchoolStudent,GraduateStudent{ } //学生


    final class MiddleSchoolStudent extends Student { }  //中学生

    final class GraduateStudent extends Student { }  //研究生
}
