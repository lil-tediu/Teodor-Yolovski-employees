import java.util.HashMap;
import java.util.Map;

public class Employee {
	private int id;
	private Map<Integer, ProjectValue> projectsDays; //every project from which date to which date
	private Map<Employee, Long> employeeDays; //how many days he worked with every other employee as a team
	
	Employee(int id) {
		this.projectsDays = new HashMap<>();
		this.employeeDays = new HashMap<>();
		this.id = id;
	}
	
	public void addProjectDays(int project, ProjectValue pv) {
		this.projectsDays.put(project, pv );
	}
	
	public void addEmployeeDays(Employee e, long days) {
		this.employeeDays.put(e, days);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public HashMap<Integer, ProjectValue> getProjectsDays() {
		return new HashMap<Integer, ProjectValue>(this.projectsDays);
	}
	
	public HashMap<Employee, Long> getEmployeeDays() {
		return new HashMap<Employee, Long>(this.employeeDays);
	}

	public int getId() {
		return id;
	}
}
