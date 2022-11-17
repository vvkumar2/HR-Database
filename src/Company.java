import java.util.*;
public class Company
{
    private ArrayList<Employee> employees;
    private String companyName;
    private String companyPassword;
    public Company ()
    {
        employees = null;
        companyName = null;
        companyPassword = null;
    }
    public Company (ArrayList<Employee> a, String  c, String  p)
    {
        employees = a;
        companyName = c;
        companyPassword = p;
    }
    public ArrayList<Employee> getList()
    {
        return this.employees;
    }
    public String getName()
    {
        return this.companyName;
    }
    public Employee getEmployee(int x)
    {
        return this.employees.get(x);
    }
    public String getPassword()
    {
        return this.companyPassword;
    }
    public void setList(ArrayList<Employee> a)
    {
        this.employees = a;
    }
}
