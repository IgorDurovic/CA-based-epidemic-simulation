public class Person {
	
	private status personStatus;
	private double infectionRate;
	
	public enum status{
		SUSCEPTIBLE, INFECTED, REMOVED
	}
	
	public double getInfectionRate() {
		return infectionRate;
	}

	public void setInfectionRate(double infectionRate) {
		this.infectionRate = infectionRate;
	}

	Person(double ir) {
		personStatus = status.SUSCEPTIBLE;
		infectionRate = ir;
	}

	public status getPersonStatus() {
		return personStatus;
	}

	public void setPersonStatus(status personStatus) {
		this.personStatus = personStatus;
	}
}
