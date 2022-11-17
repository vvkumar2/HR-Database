import java.sql.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.io.*;
public class HR_Database
{
    public static void main (String [] args) throws IOException
    {
        try {
            // Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:8080/mysql","root", "");
            System.out.print("Database is connected!");
        }

        catch(Exception e) {
            System.out.print("error!:"+e);
        }

        Scanner stan = new Scanner (System.in);
        
        ArrayList<Employee> employeeList = new ArrayList<Employee>( );
        
        System.out.println("What is the company name?");
        String compName = stan.nextLine();
        
        System.out.println("Set a password to access employee information");
        String compPass = stan.nextLine();
        
        System.out.println("How many employees are at the company");
        int numOfEmployees = stan.nextInt();
        stan.nextLine();
        
        System.out.println("Does " + compName + " pay employees commission? (yes or no)");
        String com2 = (stan.nextLine()).toLowerCase();
        
        
        double rate = 0;
        if (com2 == "yes")
        {
            System.out.println("What is the commission");
            rate = stan.nextDouble();
            stan.nextLine();
        }
         
        for(int i = 1; i<=numOfEmployees; i++)
        {
            double sales = 0;
            System.out.println("What is employee " + i + "'s name");
            String name = stan.nextLine();
            System.out.println("What is " + name + "'s position");
            String pos = stan.nextLine();
            
            if (com2 == "yes")
            {
                System.out.println("Does " + name + " get a commission? (yes or no)");
                String com = (stan.nextLine()).toLowerCase();
                if(com.equals("yes"))
                {
                        System.out.println("What is the value of " + name + "'s sales");
                        sales = stan.nextDouble();
                        stan.nextLine();
                }
            }
            System.out.println("What is " + name + "'s salary");
            double sal = stan.nextDouble();
            stan.nextLine();
            
            employeeList.add(new Employee(name, pos, sal, sales, sales * rate));
        }
        
        Company comp = new Company (employeeList, compName, compPass);
        
        System.out.println("THE LIST IS FINALIZED");
        System.out.println();
        System.out.println("Would you like to save this list (yes or no) ");
        String a = (stan.nextLine()).toLowerCase();
        
        if(a.equals("yes"))
            {
                File myFile = new File("List.TXT");
                FileWriter myWriter = new FileWriter(myFile);
                String str1 = comp.getName() + "\n" + "Number of Employees: " + comp.getList().size() + "\t Commission Rate: " + rate + "\n\n";
                for(int i = 0; i<comp.getList().size(); i++)
                {
                    Employee e = comp.getEmployee(i);
                    str1+="Name: " + e.getName() + " \t| " +"Position: " 
                        + e.getPosition() + " \t| " +"Salary: " + e.getSalary() + "\t";
                    if(e.getSales() != 0)
                    {
                        str1+="Sales: " + e.getSales() + " \t| " +"Commission: " 
                        + (e.getSales()*rate);
                    }
                    str1+="\n";
                }
                myWriter.write(str1);
                myWriter.close();

                for (int i = 0; i < comp.getList().size(); i++)
                {
                    Employee e = comp.getEmployee(i);

                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO table_name(name, position, salary, sales) VALUES (?, ?, ?, ?");

                    stmt.setString(1, e.getName());
                    stmt.setString(2, e.getPosition());
                    stmt.setDouble(3, e.getSalary());
                    stmt.setDouble(4, e.getSales());
                    
                    stmt.executeUpdate();
                }

                System.out.println("Database has been saved in file named List.TXT, and has been uploaded to hr_database");
            }
        System.out.println();
        
        int totaltype = 1;
        while(totaltype == 1)
        {
            System.out.println("Would you like to VIEW (1), DELETE FROM (2), ADD TO (3), ALTER (4) or EXIT (5) the list");
            int type = stan.nextInt();
            System.out.println();
            stan.nextLine();
            
            if(type!=5)
            {
                boolean cont = false;
                while(cont==false)
                {
                    System.out.println("This is private information for authorized personnel only: Enter the password");
                    String y = stan.nextLine();
                    if(y.equals(comp.getPassword()))
                    {
                        cont=true;
                    }
                    else
                        System.out.println("INCORRECT");
                }
            }
            while(type!=5)
            {
                if(type == 1)
                {
                    System.out.println("Here are the names of all employees: Choose the number" 
                                + " of the employee you would like to view");
                    for(int i =0; i<(comp.getList()).size(); i++)
                    {
                        Employee e = (comp.getList()).get(i);
                        System.out.println(i+1 + ": " + e.getName());
                    }
                    int x = stan.nextInt()-1;
                    stan.nextLine();
                    
                    Employee e = comp.getList().get(x);
                    
                    System.out.println();
                    System.out.println(e.getName() + "'s position: " + e.getPosition());
                    System.out.println(e.getName() + "'s salary: " + e.getSalary());
                    if(e.getSales() != 0)
                    {
                        System.out.println(e.getName() + "'s sales: " + e.getSales());
                        System.out.println(e.getName() + "'s commission: " + e.getCommission());
                    }
                    
                    
                    System.out.println();
                    System.out.println("Would you like to VIEW (1), DELETE FROM (2), ADD TO (3), ALTER (4) or EXIT (5) the list");
                    type = stan.nextInt();
                    stan.nextLine();
                }
                if(type == 2)
                {
                    System.out.println("Here are the names of all employees: Choose the number" 
                                       + " of the employee you would like to delete");
                    for(int i =0; i<(comp.getList()).size(); i++)
                    {
                        Employee e = (comp.getList()).get(i);
                        System.out.println(i+1 + ": " + e.getName());
                    }
                    int x = stan.nextInt()-1;
                    stan.nextLine();
                    
                    ArrayList <Employee> afterDelete= comp.getList();
                    afterDelete.remove(x);
                    comp.setList(afterDelete);
                    
                    System.out.println();
                    System.out.println("Here is the new list");
                    for(int i =0; i<(comp.getList()).size(); i++)
                    {
                        Employee e = (comp.getList()).get(i);
                        System.out.println(i+1 + ": " + e.getName());
                    }
                    
                    System.out.println();
                    System.out.println();
                    System.out.println("Would you like to VIEW (1), DELETE FROM (2), ADD TO (3), ALTER (4) or EXIT (5) the list");
                    type = stan.nextInt();
                    stan.nextLine();
                }
                if(type == 3)
                {
                    double sales =0;
                    System.out.println("What is the new employee's name");
                    String name = stan.nextLine();
                    System.out.println("What is " + name + "'s position");
                    String pos = stan.nextLine();
                    if (com2 == "yes")
                    {
                        System.out.println("Does " + name + " get a commission? (yes or no)");
                        String com = (stan.nextLine()).toLowerCase();
                        if(com.equals("yes"))
                        {
                            System.out.println("What is the value of " + name + "'s sales");
                            sales = stan.nextDouble();
                            stan.nextLine();
                        }
                    }
                    System.out.println("What is " + name + "'s salary");
                    double sal = stan.nextDouble();
                    stan.nextLine();
                    
                    ArrayList <Employee> afterAdd= comp.getList();
                    afterAdd.add(new Employee(name, pos, sal, sales, sales * rate));
                    comp.setList(afterAdd);
                    
                    System.out.println();
                    System.out.println("Here is the new list");
                    for(int i =0; i<(comp.getList()).size(); i++)
                    {
                        Employee e = (comp.getList()).get(i);
                        System.out.println(i+1 + ": " + e.getName());
                    }
                    
                    System.out.println();
                    System.out.println("Would you like to VIEW (1), DELETE FROM (2), ADD TO (3), ALTER (4) or EXIT (5) the list");
                    type = stan.nextInt();
                    stan.nextLine();
                }
                if(type == 4)
                {
                    System.out.println("Here are the names of all employees: Choose the number" 
                                + " of the employee you would like to edit");
                    for(int i =0; i<(comp.getList()).size(); i++)
                    {
                        Employee e = (comp.getList()).get(i);
                        System.out.println(i+1 + ": " + e.getName());
                    }
                    int x = stan.nextInt()-1;
                    stan.nextLine();
                    
                    Employee e = comp.getList().get(x);
                    
                    System.out.println();
                    System.out.println(e.getName() + "'s position: " + e.getPosition());
                    System.out.println(e.getName() + "'s salary: " + e.getSalary());
                    if(e.getSales()!=0)
                    {
                        System.out.println(e.getName() + "'s sales: " + e.getSales());
                        System.out.println(e.getName() + "'s commission: " + e.getCommission());
                    }
                    System.out.println();
                    System.out.println("Would you like to edit the name, position, salary, or sales");
                    String editThis = stan.nextLine();
                    
                    if(editThis.equals("salary"))
                    {
                        System.out.println();
                        System.out.println("What would you like to change " + 
                                            e.getName() + "'s salary to");
                        int newSal = stan.nextInt();
                        stan.nextLine();
                        e.setSalary(newSal);
                        (comp.getList()).set(x, e);
                    }
                    if(editThis.equals("position"))
                    {
                        System.out.println();
                        System.out.println("What would you like to change " + 
                                            e.getName() + "'s position to");
                        String newPos = stan.nextLine();
                        e.setPosition(newPos);
                        (comp.getList()).set(x, e);
                    }
                    if(editThis.equals("name"))
                    {
                        System.out.println();
                        System.out.println("What would you like to change " + 
                                            e.getName() + "'s name to");
                        String newName = stan.nextLine();
                        
                        e.setName(newName);
                        (comp.getList()).set(x, e);
                    }
                    if(editThis.equals("sales"))
                    {
                        System.out.println();
                        System.out.println("What would you like to change " + 
                                            e.getName() + "'s sales to");
                        double newSales = stan.nextDouble();
                        stan.nextLine();
                        
                        e.setSales(newSales);
                        e.setCommission(newSales*rate);
                        (comp.getList()).set(x, e);
                    }
                    
                    System.out.println();
                    
                    System.out.println("This is the employee's new information");
                    System.out.println(e.getName() + "'s position: " + e.getPosition());
                    System.out.println(e.getName() + "'s salary: " + e.getSalary());
                    if(e.getSales() != 0)
                    {
                        System.out.println(e.getName() + "'s sales: " + e.getSales());
                        System.out.println(e.getName() + "'s commision: " + e.getCommission());
                    }
                    
                    System.out.println();
                    System.out.println();
                    System.out.println("Would you like to VIEW (1), DELETE FROM (2), ADD TO (3), ALTER (4) or EXIT (5) the list");
                    type = stan.nextInt(); 
                    stan.nextLine();
                }
            }
            
            System.out.println();
            System.out.println("Here is the new list");
            for(int i = 0; i<comp.getList().size(); i++)
            {
                Employee e = comp.getEmployee(i);
                System.out.println("Name: " + e.getName() + " \t| " +"Position: " 
                    + e.getPosition() + " \t| " +"Salary: " + e.getSalary() + "\t");
                if(e.getSales() != 0)
                {
                    System.out.println("Sales: " + e.getSales() + " \t| " +"Commission: " 
                    + (e.getSales()*rate));
                }
            }
            
            System.out.println();
            System.out.println("Would you like to save this updated list as a file (yes or no) ");
            String b = stan.nextLine();
            if(b.equals("yes"))
            {
                File myFile = new File("updatedList.TXT");
                FileWriter myWriter = new FileWriter(myFile);
                String str1 = comp.getName() + "\n" + "Number of Employees: " + comp.getList().size() + "\t Commission Rate: " + rate + "\n\n";
                for(int i = 0; i<comp.getList().size(); i++)
                {
                    Employee e = comp.getEmployee(i);
                    str1+="Name: " + e.getName() + " \t| " +"Position: " 
                        + e.getPosition() + " \t| " +"Salary: " + e.getSalary() + "\t";
                    if(e.getSales() != 0)
                    {
                        str1+="Sales: " + e.getSales() + " \t| " +"Commission: " 
                        + (e.getSales()*rate);
                    }
                    str1+="\n";
                }
                myWriter.write(str1);
                myWriter.close();

                for (int i = 0; i < comp.getList().size(); i++)
                {
                    Employee e = comp.getEmployee(i);

                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO table_name(name, position, salary, sales) VALUES (?, ?, ?, ?");

                    stmt.setString(1, e.getName());
                    stmt.setString(2, e.getPosition());
                    stmt.setDouble(3, e.getSalary());
                    stmt.setDouble(4, e.getSales());
                    
                    stmt.executeUpdate();
                }

                System.out.println("Database has been saved in file named updatedList.TXT, and has been uploaded to hr_database");
            }
            
            System.out.println();
            System.out.println();
            System.out.println("DONE.");
            System.out.println("__________________________________________________________");
            
            System.out.println();
            System.out.println("Type 1 to at any time to re-enter the list editor");
            totaltype = stan.nextInt();
            stan.nextLine();
        }
    }
}
