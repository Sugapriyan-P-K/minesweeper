package zohoproblems;

import java.util.ArrayList;
import java.util.Scanner;

class Student 
{
    private String studentName;
    private int idNum;
    private int mark1,mark2,mark3,maths,physics,chemistry,total;
    private float cutOff;
    public Student(String studentName,int idNum,int mark1,int mark2,int mark3,int maths,int physics,int chemistry)
    {
    	this.studentName=studentName;
    	this.idNum=idNum;
    	this.mark1=mark1;
    	this.mark2=mark2;
    	this.mark3=mark3;
    	this.maths=maths;
    	this.physics=physics;
    	this.chemistry=chemistry;
    	this.total=mark1+mark2+mark3+maths+physics+chemistry;
    	this.cutOff=(float)(maths+(float)(physics/2)+(float)(chemistry/2));
    	//System.out.println(this.total+" "+this.cutOff);
    } 
    public String getName()
    {
    	return studentName;
    }
    public float getCutOff()
    {
    	return cutOff;
    }
    public int getId()
    {
    	return idNum;
    }
    public boolean greaterThan(Student s)
	{
		if((float)this.cutOff<(float)s.cutOff)
			return true;
		if((float)this.cutOff==(float)s.cutOff && this.total<s.total)
			return true;
		if(this.total==s.total && this.maths<s.maths)
			return true;
		if(this.maths==s.maths && this.physics<s.physics)
			return true;
		if(this.physics==s.physics && this.chemistry<s.chemistry)
			return true;
		return false;
	}
    public Student()
    {}
}


public class Counselling 
{
	static int totalStudents;
	static ArrayList<Student> students=new ArrayList<>();
	static int id;
	static void getStudent()
	{
		
		int mark1,mark2,mark3,maths,physics,chemistry;
		Scanner ob1=new Scanner(System.in);
		System.out.println("Enter student name:");
		String studentName=ob1.next();
		System.out.println("Enter marks(mark1 mark2 mark3 maths physics chemistry)\n(..............Do it carefully...........)");
		mark1=ob1.nextInt();
		mark2=ob1.nextInt();
		mark3=ob1.nextInt();
		maths=ob1.nextInt();
		physics=ob1.nextInt();
		chemistry=ob1.nextInt();
		Student s1=new Student(studentName,id++,mark1,mark2,mark3,maths,physics,chemistry);
		students.add(s1);
	}
	static void printStudents()
	{
		for(int i=0;i<totalStudents;i++)
		{
			Student s1=new Student();
			s1=students.get(i);
			System.out.println(s1.getId()+" "+s1.getName()+" "+s1.getCutOff());
		}
	}
	
	public static void main(String ar[])
	{
		Scanner ob=new Scanner(System.in);
		totalStudents=ob.nextInt();
		id=(int)(Math.random());
		for(int i=1;i<=totalStudents;i++)
		{
			System.out.println("\nStudent"+i+" Details...");
			getStudent();
		}
		sortStudents(students);
		printStudents();
		
	}
	static void sortStudents(ArrayList<Student> students)
	{
		Student s1=new Student();
		Student s2=new Student();
		for(int i=0;i<totalStudents-1;i++)
		{
			for(int j=i+1;j<totalStudents;j++)
			{
				s1=students.get(i);
				s2=students.get(j);
				if(s1.greaterThan(s2))
					swap(s1,s2,i,j);
			}
		}
	}
	static void swap(Student s1,Student s2,int i,int j)
	{
		Student s3=new Student();
		s3=s1;
		students.set(i, s2);
		students.set(j,s3);
		//students.remove(j);
		//students.add(j,s3);
	}

}

