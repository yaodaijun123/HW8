import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull

class KDTreeTest {

    @Test
    fun testNearestNeighbor() {
        val points = listOf(
            Point(listOf(1.0, 2.0)),
            Point(listOf(4.0, 5.0)),
            Point(listOf(8.0, 3.0))
        )

        val tree = KDTree(points)
        val query = Point(listOf(3.0, 4.0))

        assertEquals(Point(listOf(4.0, 5.0)), tree.nearestNeighbor(query))
    }

    @Test
    fun testOnePoint() {
        val points = listOf(Point(listOf(2.0, 3.0)))
        val tree = KDTree(points)

        assertEquals(points[0], tree.nearestNeighbor(Point(listOf(8.0, 9.0))))
    }

    @Test
    fun testEmptyTree() {
        val tree = KDTree(emptyList())

        assertNull(tree.nearestNeighbor(Point(listOf(1.0, 2.0))))
    }

    @Test
    fun testBacktracking() {
        val points = listOf(
            Point(listOf(4.0, 2.0)),
            Point(listOf(5.0, 5.0)),
            Point(listOf(5.5, 3.1))
        )

        val tree = KDTree(points)
        val query = Point(listOf(4.8, 3.0))

        assertEquals(Point(listOf(5.5, 3.1)), tree.nearestNeighbor(query))
    }

    @Test
    fun testThreeDimensions() {
        val points = listOf(
            Point(listOf(1.0, 2.0, 3.0)),
            Point(listOf(5.0, 5.0, 5.0)),
            Point(listOf(9.0, 9.0, 9.0))
        )

        val tree = KDTree(points)
        val query = Point(listOf(4.0, 5.0, 5.0))

        assertEquals(Point(listOf(5.0, 5.0, 5.0)), tree.nearestNeighbor(query))
    }
}
