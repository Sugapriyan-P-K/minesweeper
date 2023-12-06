import java.util.Scanner;
class FlagCall {
     static char[][] current_arr={ {'?','3','b','1'},{'2','?','1','b'},{'b','3','?','1' },{'2','b','1','?'} };
    static int len=4;
    static int flagArr[][]=new int[4][2];  int count=0;
    public static void main(String[] args) {
        System.out.println("Enter a input");
        Scanner sc=new Scanner(System.in);
        int row=sc.nextInt();
        int col=sc.nextInt();
        
        FlagCall obj=new FlagCall();
        obj.flag(row,col);
        
    }
    
     void flag(int row,int col){
         if(row>=len || col>=len)
            System.out.println("INVALID INPUT");
        else{
        if(current_arr[row][col]=='?'){
            current_arr[row][col]='F';
            flagArr[count][0]=row;
            flagArr[count][1]=col;
            //System.out.println(flagArr[count][0]+ "  "+flagArr[count][1]);
            count++;
            display();
        }
        else if(current_arr[row][col]=='F')
            System.out.println("Already flaged");
        else 
            System.out.println("Can't perform");   //Already displayed value
        }
    }
     void display(){
        int i,j;
        for(i=0;i<len;i++)    // len is the dimension of matrix ie.,4
        {
            for(j=0;j<len;j++)
               System.out.print("|  "+current_arr[i][j]+"  |");
            System.out.println("\n________________________________");
        }
    }
}
