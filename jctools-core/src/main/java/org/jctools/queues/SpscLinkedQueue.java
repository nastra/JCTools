package org.jctools.queues;

import java.util.AbstractQueue;
import java.util.Iterator;

abstract class SpscLinkedQueuePad0<E> extends AbstractQueue<E> {
    long p00, p01, p02, p03, p04, p05, p06, p07;
    long p30, p31, p32, p33, p34, p35, p36, p37;
}

abstract class SpscLinkedQueueHead<E> extends SpscLinkedQueuePad0<E> {
    protected LinkedQueueNode<E> head = new LinkedQueueNode<>();;
}

abstract class SpscLinkedQueuePad1<E> extends SpscLinkedQueueHead<E> {
    long p00, p01, p02, p03, p04, p05, p06, p07;
    long p30, p31, p32, p33, p34, p35, p36, p37;
}

abstract class SpscLinkedQueueTail<E> extends SpscLinkedQueuePad1<E> {
    protected LinkedQueueNode<E> tail = head;
}

public final class SpscLinkedQueue<E> extends SpscLinkedQueueTail<E> {
    long p00, p01, p02, p03, p04, p05, p06, p07;
    long p30, p31, p32, p33, p34, p35, p36, p37;

    @Override
    public boolean offer(E e) {
        if(e == null) {
            throw new IllegalArgumentException("null elements not allowed");
        }
        LinkedQueueNode<E> n = new LinkedQueueNode<E>(e);
        head.soNext(n);
        head = n;
        return true;
    }

    @Override
    public E poll() {
        LinkedQueueNode<E> n = tail.lvNext();
        if (n != null) {
            tail = n;
            return n.lpValue();
        }
        return null;
    }

    @Override
    public E peek() {
        return tail.lvValue();
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        LinkedQueueNode<E> temp = tail;
        int size = 0;
        while ((temp = temp.lvNext()) != null){
            size++;
        }
        return size;
    }
}