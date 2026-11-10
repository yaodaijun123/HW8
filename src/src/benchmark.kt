
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.measureTime

// Create a random point with k dimensions
fun randomPoint(k: Int): Point {
    val coordinates = List(k) { Random.nextDouble(0.0, 100.0) }
    return Point(coordinates)
}

// Compare K-d Tree and Brute Force
fun runExperiment(
    k: Int,
    numPoints: Int
): Triple<Duration, Duration, Duration> {

    // Generate training and query points
    val points = List(numPoints) { randomPoint(k) }
    val queries = List(1000) { randomPoint(k) }

    // Measure tree building time
    lateinit var tree: KDTree
    val buildTime = measureTime {
        tree = KDTree(points)
    }

    // Measure K-d Tree search time
    val kdTime = measureTime {
        for (query in queries) {
            tree.nearestNeighbor(query)
        }
    }

    // Measure Brute Force search time
    val bruteTime = measureTime {
        for (query in queries) {
            bruteForce(points, query)
        }
    }

    return Triple(buildTime, kdTime, bruteTime)
}


fun main() {
    val dimensions = listOf(2, 5, 10, 20)
    val sizes = listOf(10, 100, 1000, 10000)

    println("k, numPoints, build(ms), kd(ms), brute(ms)")

    for (k in dimensions) {
        for (n in sizes) {
            val (build, kd, brute) = runExperiment(k, n)

            println(
                "$k, $n, " +
                        "${build.inWholeMicroseconds / 1000.0}, " +
                        "${kd.inWholeMicroseconds / 1000.0}, " +
                        "${brute.inWholeMicroseconds / 1000.0}"
            )
        }
    }
}
