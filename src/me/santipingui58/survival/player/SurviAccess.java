package me.santipingui58.survival.player;

import me.santipingui58.survival.game.structures.StructureType;

public enum SurviAccess {

	CASA_SEGURIDAD,
	CASA_MENSAJERO,
	MASCOTAS,
	CASTILLO_REY,
	BANCO,
	CASA_MAGO,
	NETHER,
	END,
	WARP,
	MSG,
	CHAT_GLOBAL,
	HOME;

	public static SurviAccess fromStructureType(StructureType type) {
		switch (type) {
		case BANK:
			return BANCO;
		case KING:
			return CASTILLO_REY;
		case MAGE:
			return CASA_MAGO;
		case MAIL:
			return CASA_MENSAJERO;
		case PETS:
			return MASCOTAS;
		case SECURITY:
			return CASA_SEGURIDAD;
		default:
			return null;
		
		}
	}
	
}
