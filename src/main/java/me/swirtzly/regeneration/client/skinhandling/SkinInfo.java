package me.swirtzly.regeneration.client.skinhandling;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class SkinInfo {

	private final SkinType SKIN_TYPE;
    private final ResourceLocation TEXTURE_LOCATION;
    private final PlayerEntity PLAYER;

    public SkinInfo(PlayerEntity playerEntity, ResourceLocation resourceLocation, SkinType skinType) {
		this.SKIN_TYPE = skinType;
        this.TEXTURE_LOCATION = resourceLocation;
        this.PLAYER = playerEntity;
    }


    public ResourceLocation getTextureLocation() {
        return TEXTURE_LOCATION;
	}

    public SkinType getSkintype() {
		if (SKIN_TYPE != null) {
			return SKIN_TYPE;
		}
		return SkinType.ALEX;
	}

    public PlayerEntity getPlayer() {
        return PLAYER;
    }
	
	public enum SkinType {
		ALEX("slim", "alex"), STEVE("default", "steve");
		
		private final String type;
		private final String texturePath;
		
		SkinType(String type, String textureName) {
			this.type = type;
			this.texturePath = "textures/entity/" + textureName + ".png";
		}
		
		public String getMojangType() {
			return type;
		}

		public String getTexturePath() {
			return texturePath;
		}

		public static SkinType fromString(String id) {
			for (SkinType skinType : SkinType.values()) {
				if (skinType.getMojangType().equals(id)) {
					return skinType;
				}
			}
			return ALEX;
		}
	}
	
}
