package test.delay;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Exam {
  static final int STUDENT_SIZE = 45;

  public static void main(String[] args) {
    Random r = new Random();
    DelayQueue<Student> students = new DelayQueue<Student>();
    ExecutorService exec = Executors.newCachedThreadPool();
    for (int i = 0; i < STUDENT_SIZE; i++) {
      students.put(new Student("ѧ��" + (i + 1), 3000 + r.nextInt(9000)));
    }
    students.put(new Student.EndExam(12000, exec));// 1200Ϊ���Խ���ʱ��
    exec.execute(new Teacher(students, exec));

  }

}
