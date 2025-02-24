package corridadecarrosvirtual;

abstract interface IComp { //interface para que todas as classes que tenham um update utilizem
    abstract void update();
    abstract void update(int px, int py);
}
