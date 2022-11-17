public class Employee
{
    private String position;
    private double salary;
    private String name;
    private double sales;
    private double commission;
    public Employee (String n, String p, double s, double sa, double c)
    {
        position = p;
        salary =s;
        name = n;
        sales = sa;
        commission = c;
    }
    public double getSalary()
    {
        return this.salary;
    }
    public String getPosition()
    {
        return this.position;
    }
    public String getName()
    {
        return this.name;
    }
    public void setSalary(double x)
    {
        this.salary=x;
    }
    public void setPosition(String x)
    {
        this.position=x;
    }
    public void setName(String x)
    {
        this.name=x;
    }
    public double getSales()
    {
        return this.sales;
    }
    public double getCommission()
    {
        return this.commission;
    }
    public void setSales(double x)
    {
        this.sales=x;
    }
    public void setCommission(double x)
    {
        this.commission=x;
    }
}
