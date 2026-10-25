import java.util.Objects;

class Employee {
    private static int nextId = 1;

    private final int id;
    private String fullName;
    private int department;
    private int salary;

    public Employee(String fullName, int department, int salary) {
        this.id = nextId++;
        this.fullName = fullName;
        setDepartment(department);
        setSalary(salary);
    }

    public int getId() {
        return id;
    }
    public String getFullName() {
        return fullName;
    }
    public int getDepartment() {
        return department;
    }
    public int getSalary() {
        return salary;
    }

    public void setDepartment(int department) {
        if (department < 1 || department > 5) {
            throw new IllegalArgumentException("Отдел должен быть от 1 до 5");
        }
        this.department = department;
    }

    public void setSalary(int salary) {
        if (salary < 50 || salary > 450) {
            throw new IllegalArgumentException("Зарплата должна быть от 50 до 450");
        }
        this.salary = salary;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Employee)) return false;

        Employee employee = (Employee) object;
        return salary == employee.salary;
    }

    @Override
    public int hashCode() {
        return Objects.hash(salary);
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", Ф. И. О.='" + fullName + '\'' + ", отдел=" + department + ", зарплата=" + salary + '}';
    }

    public void printShortInfo() {
        System.out.println("Ф. И. О.: " + fullName + ", зарплата: " + salary);
    }
}

    public class EmployeeBook {
    private final Employee[] employees = new Employee[10];

    public void printAllEmployees() {
        for (Employee employee : employees) {
            if (employee != null) {
                System.out.println(employee.toString());
            }
        }
    }

    public double getAverageSalary() {
        int totalSalary = 0;
        int employeeCount = 0;

        for (Employee employee : employees) {
            if (employee == null) {
                break;
            }
            totalSalary += employee.getSalary();
            employeeCount++;
        }

        if (employeeCount == 0) {
            return 0;
        }
        return (double) totalSalary / employeeCount;
    }

    public void printTaxes(String taxType) {
        for (Employee employee : employees) {
            if (employee == null) {
                continue;
            }

            int salary = employee.getSalary();
            double taxRate;

            switch (taxType) {
                case "PROPORTIONAL":
                    taxRate = 0.13;
                    break;
                case "PROGRESSIVE":
                    if (salary <= 150) {
                        taxRate = 0.13;
                    } else if (salary <= 350) {
                        taxRate = 0.17;
                    } else {
                        taxRate = 0.21;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Тип налога должен быть PROPORTIONAL или PROGRESSIVE");
            }

            System.out.println(
                    employee.getFullName() + ": налог = " + salary * taxRate
            );
        }
    }

    public void indexSalaries(int department, int percent) {
        for (Employee employee : employees) {
            if (employee == null || employee.getDepartment() != department) {
                continue;
            }

            int newSalary = employee.getSalary() + employee.getSalary() * percent / 100;
            employee.setSalary(newSalary);
        }
    }

    public void printFirstEmployeeWithSalaryAbove(int department, int salary) {
        for (int i = 0; i < employees.length; i++) {
            Employee employee = employees[i];

            if (employee != null
                    && employee.getDepartment() == department
                    && employee.getSalary() > salary) {
                System.out.print("Позиция " + (i + 1) + ": ");
                employee.printShortInfo();
                break;
            }
        }
    }

    public void printEmployeesWithSalaryBelow(int wage, int employeeNumber) {
        int i = 0;
        int printed = 0;

        while (i < employees.length && printed < employeeNumber) {
            if (employees[i] == null) {
                break;
            }

            if (employees[i].getSalary() < wage) {
                employees[i].printShortInfo();
                printed++;
            }
            i++;
        }
    }

    public boolean containsBySalary(Employee employee) {
        for (Employee current : employees) {
            if (current != null && current.equals(employee)) {
                return true;
            }
        }
        return false;
    }

    public boolean addEmployee(Employee employee) {
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] == null) {
                employees[i] = employee;
                return true;
            }
        }
        return false;
    }

    public Employee getEmployeeById(int id) {
        for (Employee employee : employees) {
            if (employee != null && employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        EmployeeBook book = new EmployeeBook();
        Employee[] initialEmployees = {
                new Employee("Иванов Иван Иванович", 1, 100),
                new Employee("Петров Петр Петрович", 1, 150),
                new Employee("Сидорова Анна Сергеевна", 2, 200),
                new Employee("Кузнецов Алексей Игоревич", 2, 250),
                new Employee("Смирнова Мария Олеговна", 3, 300),
                new Employee("Попов Дмитрий Андреевич", 3, 350),
                new Employee("Васильева Елена Викторовна", 4, 400),
                new Employee("Соколов Сергей Николаевич", 4, 450),
                new Employee("Михайлова Ольга Павловна", 5, 120),
                new Employee("Федоров Павел Евгеньевич", 5, 180),
                new Employee("Орлова Ирина Максимовна", 1, 220)
        };

        for (Employee employee : initialEmployees) {
            System.out.println("Добавление: " + book.addEmployee(employee));
        }

        System.out.println("\nВсе сотрудники:");
        book.printAllEmployees();

        System.out.println("\nСредняя зарплата: " + book.getAverageSalary());

        System.out.println("\nПропорциональные налоги:");
        book.printTaxes("PROPORTIONAL");

        System.out.println("\nПрогрессивные налоги:");
        book.printTaxes("PROGRESSIVE");

        System.out.println("\nПервый сотрудник отдела 2 с зарплатой выше 200:");
        book.printFirstEmployeeWithSalaryAbove(2, 200);

        System.out.println("\nСотрудники с зарплатой ниже 200 (первые 3):");
        book.printEmployeesWithSalaryBelow(200, 3);

        System.out.println("\nЕсть ли сотрудник с зарплатой 250: " + book.containsBySalary(new Employee("Другой сотрудник", 5, 250)));

        System.out.println("\nСотрудник с id 3:");
        Employee found = book.getEmployeeById(3);
        System.out.println(found == null ? "Не найден" : found);

        System.out.println("\nИндексация отдела 1 на 10%:");
        book.indexSalaries(1, 10);
        book.printAllEmployees();

        System.out.println("\nДобавление 11-го сотрудника: " + book.addEmployee(new Employee("Новый сотрудник", 5, 100)));
    }
}
