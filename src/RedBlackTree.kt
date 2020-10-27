class RedBlackTree<K : Comparable<K>, V> {

    private var root: Node? = null

    inner class Node(var key: K?, var value: V?, var left: Node? = null, var right: Node? = null, var color: Boolean = RED) {
        val isRed: Boolean
            get() = color == RED
        val isBlack: Boolean
            get() = color == BLACK

        fun copy(node: Node?) {
            this.key = node?.key
            this.value = node?.value
            this.left = node?.left
            this.right = node?.right
            this.color = color
        }
    }

    fun get(key: K): V? {
        return get(root, key)
    }

    private operator fun get(node: Node?, key: K): V? {
        var x = node
        while (x != null) {
            x = when (key.compareTo(x.key!!)) {
                in Int.MIN_VALUE..-1 -> x.left
                in 1..Int.MAX_VALUE -> x.right
                else -> return x.value
            }
        }

        return null
    }


    fun put(key: K, value: V?) {
        root = put(root, key, value)
        root?.color = BLACK
    }

    private fun put(h: Node?, key: K, value: V?): Node {
        if(h == null) {
            return Node(key, value)
        }

        val cmp = key.compareTo(h.key!!)
        when {
            cmp < 0 -> h.left = put(h.left, key, value)
            cmp > 0 -> h.right = put(h.right, key, value)
            else -> h.value = value
        }

        // fix-up any right-leaning links
        if (h.right?.color == RED && h.left?.color != RED) {
            h.copy(rotateLeft(h))
        }
        if (h.left?.color == RED && h.left?.left?.color == RED) {
            h.copy(rotateRight(h))
        }
        if (h.left?.color == true && h.right?.color == RED) flipColors(h)

        return h
    }

    private fun rotateRight(h: Node): Node? {
        val x = h.left
        h.left = x?.right
        x?.right = h
        x?.color = x?.right?.color ?: false
        x?.right?.color = RED
        return x
    }

    private fun rotateLeft(h: Node): Node? {
        val x = h.right
        h.right = x?.left
        x?.left = h
        x?.color = x?.left?.color ?: false
        x?.left?.color = RED
        return x
    }

    private fun flipColors(h: Node) {
        h.color = !h.color
        h.left?.color = !(h.left?.color ?: false)
        h.right?.color = !(h.right?.color ?: false)
    }

    companion object {
        private const val RED = true
        private const val BLACK = false
    }
}
//
//fun main() {
//    val st = RedBlackBST<String, Int>()
//    val input = "THEBSAON"
//    for (i in 0 until input.length) {
//        st.put(input[i].toString(), i)
//    }
//}