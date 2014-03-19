package WayofTime.alchemicalWizardry.common.spell.complex;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellEffect;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.impactEffects.IProjectileImpactEffect;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.impactEffects.IProjectileUpdateEffect;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.impactEffects.fire.ProjectileDefaultFire;
import WayofTime.alchemicalWizardry.common.spell.complex.enhancement.SpellEnhancement;

public class SpellParadigmProjectile extends SpellParadigm
{
	public DamageSource damageSource;
	public float damage;
	public int cost;
	public List<IProjectileImpactEffect> impactList;
	public List<IProjectileUpdateEffect> updateEffectList;
	public boolean penetration;
	public int ricochetMax;
	
	public SpellParadigmProjectile()
	{
		this.damageSource = DamageSource.generic;
		this.damage = 1;
		this.cost = 0;
		this.impactList = new ArrayList();
		this.updateEffectList = new ArrayList();
		this.penetration = false;
		this.ricochetMax = 0;
	}
	
	@Override
	public void enhanceParadigm(SpellEnhancement enh) 
	{
		
	}

	@Override
	public void castSpell(World world, EntityPlayer entityPlayer, ItemStack itemStack) 
	{
		EntitySpellProjectile proj = new EntitySpellProjectile(world, entityPlayer);
		this.prepareProjectile(proj);
		world.spawnEntityInWorld(proj);
		int cost = this.getTotalCost();
		
		EnergyItems.syphonBatteries(itemStack, entityPlayer, cost);
	}
	
	public static SpellParadigmProjectile getParadigmForEffectArray(List<SpellEffect> effectList)
	{
		SpellParadigmProjectile parad = new SpellParadigmProjectile();
		
		for(SpellEffect eff : effectList)
		{
			parad.addBufferedEffect(eff);
		}
		
		return parad;
	}
	
	public void prepareProjectile(EntitySpellProjectile proj)
	{
		proj.setDamage(damage);
		proj.setImpactList(impactList);
		proj.setUpdateEffectList(updateEffectList); 
		proj.setPenetration(penetration);
		proj.setRicochetMax(ricochetMax); 
		proj.setSpellEffectList(bufferedEffectList);
	}
	
	public void addImpactEffect(IProjectileImpactEffect eff)
	{
		if(eff!=null)
		{
			this.impactList.add(eff);
		}
	}
	
	public void addUpdateEffect(IProjectileUpdateEffect eff)
	{
		if(eff!=null)
		{
			this.updateEffectList.add(eff);
		}
	}

	@Override
	public int getDefaultCost() 
	{
		return 50;
	}
	
}
