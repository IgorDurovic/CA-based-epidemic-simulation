import java.util.Random;

public class Person 
{
	private int age;
	private int gender;
	private int race;
	private status personStatus;
	private double infectionRate;
	public int recoveryTime = 84;
	private double mobility;

	private double ageDistro[] = {0.013818, 0.055317, 
			0.145565, 0.138646, 0.135573, 0.162613, 
			0.134834, 0.087247, 0.066037, 0.044842, 
			0.015508};
	
	private double raceDistro[] = {0.009, 0.05, 
			0.126, 0.164, 0.724};
	
	public enum status
	{
		SUSCEPTIBLE, INFECTED, REMOVED
	}

	Person(double ir) 
	{
		personStatus = status.SUSCEPTIBLE;
		infectionRate = ir;
		Random rand = new Random();
		
		//age is assigned to the person object based on US age distribution
		double ageProb = rand.nextDouble();
		double totalProb = 0;
		for(int i = 0; i < ageDistro.length; i++){
			double temp = totalProb;
			totalProb += ageDistro[i];
			if(temp < ageProb && ageProb <= totalProb){
				age = i+1;
			}
		}
		
		//race is assigned based on census data (2010) on race distribution
		totalProb = 0;
		double raceProb = rand.nextDouble();
		for(int i = 0; i < raceDistro.length; i++){
			double temp = totalProb;
			totalProb += raceDistro[i];
			if(temp < raceProb && raceProb <= totalProb){
				race = i+1;
			}
		}
		
		//gender is assigned, equal probability is assumed
		double genderProb = rand.nextDouble();
		if(genderProb > 0.5){
			gender = 1;
		}
		else{
			gender = 0;
		}
	}
	
	Person(Person p){
		personStatus = p.getPersonStatus();
		gender = p.getGender();
		age = p.getAge();
		race = p.getRace();
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

	public void setPersonStatus(Person.status personStatus) 
	{
		this.personStatus = personStatus;
	}

	public int getRecoveryTime()
	{
		return recoveryTime;
	}

	public void setRecoveryTime(int recoveryTime)
	{
		this.recoveryTime = recoveryTime;
	}

	public double getMobility() {
		return mobility;
	}

	public void setMobility(double mobility) {
		this.mobility = mobility;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getRace() {
		return race;
	}

	public void setRace(int race) {
		this.race = race;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
