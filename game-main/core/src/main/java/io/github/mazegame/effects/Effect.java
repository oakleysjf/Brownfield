package io.github.mazegame.effects;

public abstract class Effect {
    public int duration;

    /** Creates the effect with the specific duration.
     * 
     * @param duration the duration of the effect.
     */
    public Effect (int duration) {
        this.duration = duration;
    }

    /** Applies the effect assositated by this given effect. */
    public abstract void applyEffect();

    @Override
    public boolean equals(Object other) {
        return other.getClass() == this.getClass();
    }
}
