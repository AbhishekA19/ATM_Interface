package atmProject;

import java.util.Scanner;

import java.sql.*;

public class atm {

	static Scanner sc =new Scanner(System.in);
	static int operation;
	static float balance = 0;
	static String name=null;
	static int choice;
	static int amount;
	static int take_amount; 
	static String act_holder;
	static float money;
	static String reg;
	static int id;
	static int ac_no;
	static int bal;
	static int sub;
	
	public static void main(String[] args) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3308/atm","root","");
			Statement stmt = con.createStatement();
			System.out.println("************************************************************\n");
			System.out.println("\t******************** Welcome ********************");
			
			while(true) {
				
				System.out.println("\n\t\tPress 1 TO SIGNUP \n\t\tPress 2 TO LOGIN \n\t\tPress 0 TO EXIT");
				System.out.println("************************************************************\n");
				
				System.out.print("\n\t\tChoose Operation :- ");
				operation=sc.nextInt();
				
				switch(operation){
				case 1:{
					reg="INSERT INTO `list`(`id`, `ac_no`, `name`, `balance`) VALUES (?,?,?,?)";
					
					PreparedStatement preparedStmt = con.prepareStatement(reg);
					System.out.print("\n\t\tEnter Your ID :- ");
					id=sc.nextInt();
					
					System.out.print("\n\t\tEnter Your Pin :- ");
					ac_no=sc.nextInt();
					
					System.out.print("\n\t\tEnter Your Name :- ");
					name=sc.next();
					
					//System.out.print("\n\t\tEnter Your Balance :- ");
					//balance=sc.nextFloat();
					
					
					preparedStmt.setInt(1,id);
					preparedStmt.setInt(2,ac_no);
					preparedStmt.setString(3,name);
					preparedStmt.setFloat(4,balance);
					preparedStmt.execute();
					System.out.println("\n\t***** Registration Completed.Thank You!! *****\n");
					System.out.println("************************************************************\n");
					break;
				}
				case 2:{
					
					System.out.print("\n\tEnter Your Pin Number :- ");
					int pin =sc.nextInt();
					ResultSet rs=stmt.executeQuery("SELECT * FROM list WHERE ac_no ="+pin);
					int count=0; 
					while(rs.next()){
						name=rs.getString(3);
						balance=rs.getInt(4);
						count++;
					}
					if(count>0){
						System.out.println("\n************************************************************\n");
						System.out.println("\t\t Hello " + name +"!\n");
						while(true){
							System.out.println("\n************************************************************\n");
							System.out.println("\t\tPress 1 To Check Balance.");
							System.out.println("\t\tPress 2 To Add Amount.");
							System.out.println("\t\tPress 3 To Withdraw Amount.");
							System.out.println("\t\tPress 4 TO Transfer Money.");
							System.out.println("\t\tPress 5 To print reciept.");
							System.out.println("\t\tPress 0 to Exit");
							System.out.println("************************************************************\n");

							System.out.print("\tEnter Your Choice :- ");
							choice=sc.nextInt();

							switch(choice){
								case 1:{
									System.out.println("\n\t\tYour Balance is : " +balance+" Rs/-");
									System.out.println();
									break;
								}
								case 2:{
									System.out.print("\t\tHow Much amount did you want to add :- ");
									amount =sc.nextInt();
									balance = balance + amount ;
									bal=stmt.executeUpdate("UPDATE list SET balance = "+balance+" WHERE ac_no = "+pin);
									System.out.println("\n\t\tSuccesfully Added!!");
									System.out.println("\t\tNow your Current Balance is :- "+ balance+ " Rs/-");
									System.out.println();
									break;
								}
								case 3:{
									System.out.print("\t\tHow much amount you want to withdraw :- ");
									take_amount=sc.nextInt();
									if(take_amount>balance){
										System.out.println("\n\t\tYour balanace is insufficient! Please Check Your Balance. ");
									}else{
										balance = balance - take_amount;
										sub=stmt.executeUpdate("UPDATE list SET balance= "+balance+" WHERE ac_no= "+pin);
									    System.out.println("\n\t\tSuccesfully withdraw!!");
									    System.out.println("\t\tNow Your Current Balance is :- "+balance+" Rs/-");	
									    System.out.println();
									    }
									break;
								}
								case 4:{
									System.out.print("\t\tEnter Account Holder Name:- ");
									act_holder=sc.next();
							        
							        System.out.print("\n\t\tEnter Amount to Transfer:- ");
							        money=sc.nextFloat();
							        
							        if(balance>=money){
							            if(money<=50000){
							               
							                balance = balance - money;
							                sub=stmt.executeUpdate("UPDATE list SET balance= "+balance+" WHERE ac_no= "+pin);
							                System.out.println("\n\t\tSuccessfully Transferred!!");
							                System.out.println("\t\tRs "+money+" Transfered to "+act_holder+".\n");

							            }else{
							                System.out.println("\t\tLimit Exceeded! Your Limit is 50000/-Rs.");
							            }
							        }else{
							            System.out.println("\t\tInsufficient Balance !");
							        }
							        break;
								}
								case 5:{
									System.out.println("\t\tHere is Your Transaction History.\n");
									System.out.println("\t\tYour current balance is : "+balance+" Rs/-");
									System.out.println("\t\tAmount added: "+amount+" Rs/-");
									System.out.println("\t\tAmount withdraw: "+take_amount+" Rs/-");
									System.out.println("\t\t"+money+" Rs/- Transfer to "+act_holder);
									System.out.println();
									break;
								}
							}
							if(choice==0){
								break;
							}
						}
					}
					else{
						System.out.println("\n\tWrong Pin Entered! Check Your Pin and try again..");
						System.out.println("************************************************************\n");
					}
					break;
				}
				
				}
				if(operation==0) {
					break;
				}
			}
			
			
		}
		catch(Exception e){
			System.out.println(e);
		}

		System.out.println("************************************************************\n");
	}
	

}