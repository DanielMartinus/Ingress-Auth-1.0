package com.danielmartinus.intelchecker;

public class IntelUser {
	
	private String agentName;
	private String faction;
	private String email;
	private String abilityPoints;
	private String energy;
	private String invites;

	public IntelUser(String agentName, String faction, String email, String abilityPoints, String energy, String invites) {
		this.agentName = agentName;
		this.faction = faction;
		this.email = email;
		this.abilityPoints = abilityPoints;
		this.energy = energy;
		this.invites = invites;
	}

	public String getAgentName() {
		return agentName;
	}

	public String getFaction() {
		return faction;
	}

	public String getEmail() {
		return email;
	}

	public String getAbilityPoints() {
		return abilityPoints;
	}

	public String getEnergy() {
		return energy;
	}

	public String getInvites() {
		return invites;
	}
}
