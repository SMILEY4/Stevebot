package stevebot.core.math.vectors.vec2;


public class ConstVector2<V extends IVector2> implements IVector2 {


    private final V vec;


    public ConstVector2(V src) {
        this.vec = (V) src.copy();
    }


    @Override
    public int getDimensions() {
        return vec.getDimensions();
    }


    @Override
    public VectorType getVectorType() {
        return vec.getVectorType();
    }


    @Override
    public V setAt(int index, Number value) {
        throw new UnsupportedOperationException();
    }


    @Override
    public int getInt(int index) {
        return vec.getInt(index);
    }


    @Override
    public long getLong(int index) {
        return vec.getLong(index);
    }


    @Override
    public float getFloat(int index) {
        return vec.getFloat(index);
    }


    @Override
    public double getDouble(int index) {
        return vec.getDouble(index);
    }


    @Override
    public V negate() {
        throw new UnsupportedOperationException();
    }


    @Override
    public V normalize() {
        throw new UnsupportedOperationException();
    }


    @Override
    public IVector2 copy() {
        return new ConstVector2<V>(vec);
    }


    @Override
    public Number length2Gen() {
        return vec.length2Gen();
    }


    @Override
    public Number lengthGen() {
        return vec.lengthGen();
    }


    @Override
    public V setLength(Number length) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V limitLength(Number maxLength) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V clampLength(Number minLength, Number maxLength) {
        throw new UnsupportedOperationException();
    }


    @Override
    public Number componentSumGen() {
        return vec.componentSumGen();
    }


    @Override
    public Number componentMinGen() {
        return vec.componentMinGen();
    }


    @Override
    public Number componentMaxGen() {
        return vec.componentMaxGen();
    }


    @Override
    public V clampComponents(Number min, Number max) {
        throw new UnsupportedOperationException();
    }


    @Override
    public boolean isUnit() {
        return vec.isUnit();
    }


    @Override
    public boolean isZero() {
        return vec.isZero();
    }


    @Override
    public V set(IVector2 v) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V set(Number x, Number y) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V set(Number xy) {
        throw new UnsupportedOperationException();
    }


    @Override
    public int getIntX() {
        return vec.getIntX();
    }


    @Override
    public int getIntY() {
        return vec.getIntY();
    }


    @Override
    public long getLongX() {
        return vec.getLongX();
    }


    @Override
    public long getLongY() {
        return vec.getLongY();
    }


    @Override
    public float getFloatX() {
        return vec.getFloatX();
    }


    @Override
    public float getFloatY() {
        return vec.getFloatY();
    }


    @Override
    public double getDoubleX() {
        return vec.getDoubleX();
    }


    @Override
    public double getDoubleY() {
        return vec.getDoubleY();
    }


    @Override
    public Vector2i toIntVector() {
        throw new UnsupportedOperationException();
    }


    @Override
    public Vector2l toLongVector() {
        throw new UnsupportedOperationException();
    }


    @Override
    public Vector2f toFloatVector() {
        throw new UnsupportedOperationException();
    }


    @Override
    public Vector2d toDoubleVector() {
        throw new UnsupportedOperationException();
    }


    @Override
    public ConstVector2<? extends IVector2> toConstVector() {
        throw new UnsupportedOperationException();
    }


    @Override
    public V add(IVector2 vec) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V add(Number x, Number y) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V add(Number xy) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V sub(IVector2 vec) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V sub(Number x, Number y) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V sub(Number xy) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V mul(IVector2 vec) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V mul(Number x, Number y) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V scale(Number xy) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V div(IVector2 vec) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V div(Number x, Number y) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V div(Number xy) {
        throw new UnsupportedOperationException();
    }


    @Override
    public Number crossGen(IVector2 vec) {
        return this.vec.crossGen(vec);
    }


    @Override
    public Number crossGen(Number x, Number y) {
        return vec.crossGen(x, y);
    }


    @Override
    public Number dotGen(IVector2 vec) {
        return this.vec.dotGen(vec);
    }


    @Override
    public Number dotGen(Number x, Number y) {
        return vec.dotGen(x, y);
    }


    @Override
    public Number dist2Gen(IVector2 vec) {
        return this.vec.dist2Gen(vec);
    }


    @Override
    public Number dist2Gen(Number x, Number y) {
        return vec.dist2Gen(x, y);
    }


    @Override
    public Number distGen(IVector2 vec) {
        return this.vec.distGen(vec);
    }


    @Override
    public Number distGen(Number x, Number y) {
        return vec.distGen(x, y);
    }


    @Override
    public Number angleRadGen(IVector2 vec) {
        return this.vec.angleRadGen(vec);
    }


    @Override
    public Number angleRadGen(Number x, Number y) {
        return vec.angleRadGen(x, y);
    }


    @Override
    public Number angleDegGen(IVector2 vec) {
        return this.vec.angleDegGen(vec);
    }


    @Override
    public Number angleDegGen(Number x, Number y) {
        return vec.angleDegGen(x, y);
    }


    @Override
    public V rotateRad(Number angleRad) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V rotateDeg(Number angleDeg) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V project(IVector2 vec) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V reflect(IVector2 vec) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V refract(IVector2 vec, float eta) {
        throw new UnsupportedOperationException();
    }


    @Override
    public boolean isPerpendicular(IVector2 vec) {
        return this.vec.isPerpendicular(vec);
    }


    @Override
    public boolean compare(IVector2 vec) {
        return this.vec.compare(vec);
    }


    @Override
    public String toString() {
        return "ConstVector2." + this.hashCode() + "(" + this.getFloatX() + ", " + this.getFloatY() + ")";
    }


}
