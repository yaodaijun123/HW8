class KDNode(
    val point: Point,
    val axis: Int,
    val left: KDNode?,
    val right: KDNode?
)

class KDTree(points: List<Point>) {

    private val k = if (points.isEmpty()) 0 else points[0].coordinates.size

    private val root = buildTree(points, 0)

    // Build the tree using the median
    private fun buildTree(points: List<Point>, depth: Int): KDNode? {
        if (points.isEmpty()) return null

        // Choose which dimension to split
        val axis = depth % k

        // Sort points by the current dimension
        val sorted = points.sortedBy { it.coordinates[axis] }

        // Find the middle point
        val middle = sorted.size / 2

        // Build left and right subtrees
        val left = buildTree(sorted.subList(0, middle), depth + 1)
        val right = buildTree(sorted.subList(middle + 1, sorted.size), depth + 1)

        return KDNode(sorted[middle], axis, left, right)
    }

    // Print the tree to check its structure
    fun printTree() {
        printNode(root, 0)
    }

    // Find the nearest point to the query
    fun nearestNeighbor(query: Point): Point? {
        if (root == null) return null
        return search(root, query, root.point)
    }

    // Search the tree recursively
    private fun search(node: KDNode?, query: Point, best: Point): Point {
        if (node == null) return best

        var nearest = best

        // Check the current point
        if (distance(node.point, query) < distance(nearest, query)) {
            nearest = node.point
        }

        val axis = node.axis
        val diff = query.coordinates[axis] - node.point.coordinates[axis]

        // Search the closer side first
        val first = if (diff < 0) node.left else node.right
        val second = if (diff < 0) node.right else node.left

        nearest = search(first, query, nearest)

        // Check if the other side could have a closer point
        if (diff * diff <= distance(nearest, query) * distance(nearest, query)) {
            nearest = search(second, query, nearest)
        }

        return nearest
    }


    private fun printNode(node: KDNode?, depth: Int) {
        if (node == null) return

        println("  ".repeat(depth) + "${node.point.coordinates} axis=${node.axis}")

        printNode(node.left, depth + 1)
        printNode(node.right, depth + 1)
    }
}
