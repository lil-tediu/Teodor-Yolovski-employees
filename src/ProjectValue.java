import java.util.Date;

public class ProjectValue {
	private Date dateFrom;
	private Date dateTo;
	
	
	public ProjectValue(Date dateFrom, Date dateTo) {
		super();
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateFrom == null) ? 0 : dateFrom.hashCode());
		result = prime * result + ((dateTo == null) ? 0 : dateTo.hashCode());
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
		ProjectValue other = (ProjectValue) obj;
		if (dateFrom == null) {
			if (other.dateFrom != null)
				return false;
		} else if (!dateFrom.equals(other.dateFrom))
			return false;
		if (dateTo == null) {
			if (other.dateTo != null)
				return false;
		} else if (!dateTo.equals(other.dateTo))
			return false;
		return true;
	}
	
	public Date getDateFrom() {
		return dateFrom;
	}
	
	public Date getDateTo() {
		return dateTo;
	}
}
