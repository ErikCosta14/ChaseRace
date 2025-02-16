package corridadecarrosvirtual;

abstract interface IComp {
    abstract void update();
    abstract void update(int px, int py);
    abstract void load(String carro);
}
