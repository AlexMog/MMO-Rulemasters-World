package alexmog.rulemastersworld.entity;

import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.gamemodes.GameMode;

public class StatsEntity extends Entity {
    // Constants
    public static float CRITICAL_MODIFICATOR = 0.01f;
    public static float PARRY_MODIFICATOR = 0.01f;
    public static float DODGE_MODIFICATOR = 0.01f;
    public static float DAMAGE_MODIFICATOR = 0.5f;
    public static float ARMOR_REDUCTION = 0.0001f;
    public static float HP_MODIFICATOR = 5f;
    public static float MANA_MODIFICATOR = 3f;
    // Stats principales
    protected int mStr, mAgi, mInt,
                    mEnd, mArmor, mBonusMana,
                    mBonusDamages, mManaRegen, mBonusHp;
    protected float mBonusDodge, mBonusParry, mBonusCriticalHit, mAtkSpeed;
    
    public StatsEntity(Vector2f position, Vector2f size, GameMode gameMode) {
        super(position, size, gameMode);
    }
    
    public void setStats(StatsEntity e) {
        mStr = e.getStr();
        mAgi = e.getAgi();
        mInt = e.getInt();
        mEnd = e.getEnd();
        mArmor = e.getArmor();
        mBonusMana = e.getBonusMana();
        mBonusDamages = e.getBonusDamages();
        mManaRegen = e.getManaRegen();
        mBonusHp = e.getBonusHp();
        mBonusDodge = e.getBonusDodge();
        mBonusParry = e.getBonusParry();
        mBonusCriticalHit = e.getBonusCriticalHit();
        mAtkSpeed = e.getAtkSpeed();
    }
    
    public float getBonusCriticalHit() {
        return mBonusCriticalHit;
    }
    
    public float getBonusParry() {
        return mBonusParry;
    }
    
    public float getBonusDodge() {
        return mBonusDodge;
    }
    
    public void setBonusHp(int bonusHp) {
        mBonusHp = bonusHp;
    }
    
    public int getMaxMana() {
        return (int)(mBonusMana + (mInt * MANA_MODIFICATOR));
    }
    
    public int getMaxHp() {
        return (int)(mBonusHp + (mEnd * HP_MODIFICATOR));
    }
    
    public float getDamages() {
        return mBonusDamages + (DAMAGE_MODIFICATOR * mStr);
    }
    
    public float getDamagesReduction() {
        return getArmor() * ARMOR_REDUCTION;
    }
    
    public int getBonusDamages() {
        return mBonusDamages;
    }
    
    public float getAtkSpeed() {
        return mAtkSpeed;
    }
    
    public int getManaRegen() {
        return mManaRegen;
    }
    
    public float getCriticalPercent() {
        return mBonusCriticalHit + (CRITICAL_MODIFICATOR * mAgi);
    }
    
    public float getParryPercent() {
        return mBonusParry + (PARRY_MODIFICATOR * mStr);
    }
    
    public float getDodgePercent() {
        return mBonusDodge + (DODGE_MODIFICATOR * mAgi);
    }
    
    public int getArmor() {
        return mArmor;
    }
    
    public int getStr() {
        return mStr;
    }
    
    public int getAgi() {
        return mAgi;
    }
    
    public int getInt() {
        return mInt;
    }
    
    public int getEnd() {
        return mEnd;
    }
    
    public int getBonusMana() {
        return mBonusMana;
    }
    
    public int getBonusHp() {
        return mBonusHp;
    }

    public void setStr(int str) {
        mStr = str;
    }
    
    public void setAgi(int agi) {
        mAgi = agi;
    }
    
    public void setInt(int intel) {
        mInt = intel;
    }
    
    public void setEnd(int end) {
        mEnd = end;
    }
    
    public void setBonusMana(int bonusMana) {
        mBonusMana = bonusMana;
    }
    
    public void setArmor(int armor) {
        mArmor = armor;
    }
    
    public void setBonusDodge(float bonusDodge) {
        mBonusDodge = bonusDodge;
    }
    
    public void setBonusParry(float bonusParry) {
        mBonusParry = bonusParry;
    }
    
    public void setBonusDamages(int bonusDamages) {
        mBonusDamages = bonusDamages;
    }
    
    public void setAtkSpeed(float f) {
        mAtkSpeed = f;
    }
    
    public void setBonusCriticalHit(float bonusCriticalHit) {
        mBonusCriticalHit = bonusCriticalHit;
    }
    
    public void setManaRegen(int manaRegen) {
        mManaRegen = manaRegen;
    }
}
