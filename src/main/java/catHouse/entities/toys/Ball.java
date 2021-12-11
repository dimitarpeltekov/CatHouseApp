package catHouse.entities.toys;

public class Ball extends BaseToy{
    private final static int SOFTNESS_BALL = 1;
    private final static double BALL_PRICE = 10;

    public Ball() {
        super(SOFTNESS_BALL, BALL_PRICE);
    }
}
