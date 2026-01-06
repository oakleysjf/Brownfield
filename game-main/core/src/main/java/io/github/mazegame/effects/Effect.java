package io.github.mazegame.effects;

/** Abstract base class for temporary effects applied to the player. */
public abstract class Effect {
    public int duration;

    /** Creates the effect with the specific duration.
     * 
     * @param duration the duration of the effect.
     */
    public Effect (int duration) {
        this.duration = duration;
    }

    /** Applies the effect associated with this effect and decrements its duration. */
    public abstract void applyEffect();

    @Override
    public boolean equals(Object other) {
        return other.getClass() == this.getClass();
    }
}
