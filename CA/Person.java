public class Person 
{
	private status personStatus;
	private double infectionRate;
	public int recoveryTime;

	public enum status
	{
		SUSCEPTIBLE, INFECTED, REMOVED
	}

	Person(double ir) 
	{
		personStatus = status.SUSCEPTIBLE;
		infectionRate = ir;
	}
	
	Person(Person p){
		personStatus = p.getPersonStatus();
		infectionRate = p.getInfectionRate();
		recoveryTime = p.getRecoveryTime();
	}

	public double getInfectionRate() 
	{
		return infectionRate;
	}

	public void setInfectionRate(double infectionRate) 
	{
		this.infectionRate = infectionRate;
	}

	public status getPersonStatus() 
	{
		return personStatus;
	}

	public void setPersonStatus(Person.status personStatus, int rt) 
	{
		this.personStatus = personStatus;
		recoveryTime = rt;
	}

	public int getRecoveryTime()
	{
		return recoveryTime;
	}

	public void setRecoveryTime(int recoveryTime)
	{
		this.recoveryTime = recoveryTime;
	}
}
