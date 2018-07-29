import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Application {
	static Set<Employee> employees = new HashSet<Employee>();
	
	//No get method in Set 
	private static Employee getEqual(Employee e, Set<Employee> set) {
		for (Iterator<Employee> it = set.iterator(); it.hasNext(); ) {
			Employee emp = it.next();
			if (emp.equals(e)) {
				return emp;
			}
		}
		return null;	
	}
	
	public static void fillEmployeeDays() {
		for (Employee e : Application.employees) {
			for (Employee e1 : Application.employees) {
				if (!e.equals(e1)) {
					long days=0;
					for(Entry<Integer, ProjectValue> project : e.getProjectsDays().entrySet()) {
						if(e1.getProjectsDays().containsKey(project.getKey())) {
							days+=getNumerOfOverLappingDays(e.getProjectsDays().get(project.getKey()), e1.getProjectsDays().get(project.getKey()));
						}
						e.addEmployeeDays(e1, days);
					}
				}
			}
		}
	}
	
	public static long getMostWorkingDaysByTwoEmployees() {
		long result = 0;
		for (Employee e : Application.employees) {
			for (Entry<Employee, Long> entry : e.getEmployeeDays().entrySet()) {
				if (entry.getValue()>result) {
					result = entry.getValue();
				}
			}
		}
		return result;
	}
	
	
	public static Set<Employee> getTwoThatWorkedMostTimeAsATeam() {
		Set<Employee> result = new HashSet<>();
		for (Employee e : Application.employees) {
			for (Entry<Employee, Long> entry : e.getEmployeeDays().entrySet()) {
				if (entry.getValue()==getMostWorkingDaysByTwoEmployees()) {
					result.add(e);
					result.add(entry.getKey());
					return result;
				}
			}
		}
		return result;
	}
	
	
	public static long getDifferenceDays(Date d1, Date d2) {
	    long diff = d2.getTime() - d1.getTime();
	    return Math.abs(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
	}
	
	public static long getNumerOfOverLappingDays(ProjectValue pj1, ProjectValue pj2) {
		if (pj1.getDateFrom().after(pj2.getDateTo()) || pj1.getDateTo().before(pj2.getDateFrom())) {
			return 0;
		} else if (pj1.getDateFrom().before(pj2.getDateFrom()) && pj1.getDateTo().after(pj2.getDateTo())) {
			return getDifferenceDays(pj2.getDateFrom(), pj2.getDateTo());
		} else if (pj1.getDateFrom().after(pj2.getDateFrom()) && pj1.getDateTo().before(pj2.getDateTo())) {
			return getDifferenceDays(pj1.getDateFrom(), pj1.getDateTo());
		} else if (pj1.getDateFrom().after(pj2.getDateFrom()) && pj1.getDateTo().after(pj2.getDateTo())) {
			return getDifferenceDays(pj1.getDateFrom(), pj2.getDateTo());
		} else  {
			return getDifferenceDays(pj2.getDateFrom(), pj1.getDateTo());
		}
	}
	
	public static void load(String filepath) throws NumberFormatException, ParseException, FileNotFoundException, DateException {
		String employeeId, projectId, dateFrom, dateTo;
		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd" );
		Scanner scan = new Scanner(new File(filepath));
	    while (scan.hasNextLine()) {
	       String line = scan.nextLine();
	       String[] array = line.split(", ");
	       employeeId=array[0]; projectId = array[1]; dateFrom = array[2]; dateTo = array[3];
	       Employee e = new Employee(Integer.parseInt(employeeId));
	       if (employees.contains(e)) { 
	    	 //if some employee has more than one project through the time
	    	 e = getEqual(e, employees);
	       }
	       if (dateTo.equals("NULL")) {
	    	   dateTo = sdt.format(new Date());
	       }
	       if (sdt.parse(dateTo).after(new Date()) || sdt.parse(dateFrom).after(new Date())) {
	    	   throw new DateException();
	       }
	       if (sdt.parse(dateTo).before(sdt.parse(dateFrom))) {
	    	   throw new DateException();
	       }
	       e.addProjectDays(Integer.parseInt(projectId), new ProjectValue(sdt.parse(dateFrom), sdt.parse(dateTo)));
	       Application.employees.add(e);
	    }
	    scan.close();
	}
	
	
	public static void main(String[] args) {
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter filenam or filepath with the data: ");
			String file = scanner.nextLine();
			Application.load(file);
			fillEmployeeDays();
			Set<Employee> result = getTwoThatWorkedMostTimeAsATeam();
			System.out.print("The ids of the two employees that worked most days as a team are: ");
			for (Employee e : result) {
				System.out.print(e.getId() + " ");
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't load, the file was not found!");
			e.printStackTrace();
		} catch (DateException e1) {
			System.out.println("Couldn't load! You cannot add date after today's date and starting date after finishing date!");
			e1.printStackTrace();
		} catch (NumberFormatException e1) {
				System.out.println("The string doesn't have an appropriate format!");
			e1.printStackTrace();
		} catch (ParseException e1) {
				System.out.println("Error while parsing!");
			e1.printStackTrace();
		}
	}
}
