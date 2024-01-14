public class DistanceAttribute implements Comparable<DistanceAttribute> {

    private final String train;
    private final double distance;

    public DistanceAttribute(String train, double distance) {
        this.train = train;
        this.distance = distance;
    }

    public String getTrainingAttribute() {
        return train;
    }
    @Override
    public int compareTo(DistanceAttribute otherVector) {
        return Double.compare(this.distance, otherVector.distance);
    }

}
