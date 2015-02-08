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

	public void setPersonStatus(status personStatus, int rt) 
	{
		this.personStatus = personStatus;
		recoveryTime = rt;
	}

	public void getRecoveryTime()
	{
		return recoveryTime;
	}

	public void setRecoveryTime(int recoveryTime)
	{
		this.recoveryTime = recoveryTime;
	}
}
