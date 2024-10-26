package com.fanz.oways.model

class LinkedList<T> {
    private var head: Node<T>? = null
    private var size = 0

    fun add(value: T) {
        if (head == null) {
            head = Node(value, index = 0)
        } else {
            var current = head
            while (current?.next != null) {
                current = current.next
            }
            current?.next = Node(value, index = size)
        }
        size++
    }

    fun remove(value: T): Boolean {
        if (head == null) return false

        if (head?.value == value) {
            head = head?.next
            updateIndices()
            size--
            return true
        }

        var current = head
        while (current?.next != null) {
            if (current.next?.value == value) {
                current.next = current.next?.next
                updateIndices()
                size--
                return true
            }
            current = current.next
        }
        return false
    }

    private fun updateIndices() {
        var current = head
        var currentIndex = 0
        while (current != null) {
            current.index = currentIndex
            currentIndex++
            current = current.next
        }
    }

    fun getAt(index: Int): T? {
        if (index < 0 || index >= size) throw IndexOutOfBoundsException("Index out of bounds")

        var current = head
        while (current != null && current.index != index) {
            current = current.next
        }
        return current?.value
    }


    fun size() = size

    fun findNext(index: Int): Int {

        var nextPosition = -1
        for (i in index.rangeUntil(size)){
            var current = head
            while (current != null && current.index != i) {
                current = current.next
            }
            if ((current?.value as PlayerSelectedItem).player ==null){
                nextPosition = i
                break
            }
        }
        if (nextPosition == -1 && index>0){
            for (i in 0.rangeUntil(index)){
                var current = head
                while (current != null && current.index != i) {
                    current = current.next
                }
                if ((current?.value as PlayerSelectedItem).player ==null){
                    nextPosition = i
                    break
                }
            }
        }

        return nextPosition

    }


}

data class Node<T>(var value: T, var next: Node<T>? = null, var index: Int = 0)
