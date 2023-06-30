package me.santipingui58.survival.game.legal;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import me.santipingui58.survival.HikaSurvival;

public class LegalManager {

	public HikaSurvival hikaSurvival;
	public LegalManager(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
	}
	
	public Set<HikaContract> contracts = new HashSet<HikaContract>(); 
	
	
	public HikaContract getContractByUUID(UUID uuid) {
		for (HikaContract c : this.contracts) {
			if (c.getUuid().equals(uuid)) {
				return c;
			}
		}
		return null;
	}

	
	
}
