package kotlin

import java.util.*

/**
 * Returns *true* if all elements match the given *predicate*
 *
 * @includeFunctionBody ../../test/CollectionTest.kt all
 */
public inline fun <T> java.lang.Iterable<T>.all(predicate: (T) -> Boolean) : Boolean {
    for (element in this) if (!predicate(element)) return false
    return true
}

/**
 * Returns *true* if any elements match the given *predicate*
 *
 * @includeFunctionBody ../../test/CollectionTest.kt any
 */
public inline fun <T> java.lang.Iterable<T>.any(predicate: (T) -> Boolean) : Boolean {
    for (element in this) if (predicate(element)) return true
    return false
}

/**
 * Appends the string from all the elements separated using the *separator* and using the given *prefix* and *postfix* if supplied
 *
 * If a collection could be huge you can specify a non-negative value of *limit* which will only show a subset of the collection then it will
 * a special *truncated* separator (which defaults to "..."
 *
 * @includeFunctionBody ../../test/CollectionTest.kt appendString
 */
public inline fun <T> java.lang.Iterable<T>.appendString(buffer: Appendable, separator: String = ", ", prefix: String = "", postfix: String = "", limit: Int = -1, truncated: String = "..."): Unit {
    buffer.append(prefix)
    var count = 0
    for (element in this) {
        if (++count > 1) buffer.append(separator)
        if (limit < 0 || count <= limit) {
            val text = if (element == null) "null" else element.toString()
            buffer.append(text)
        } else break
    }
    if (limit >= 0 && count > limit) buffer.append(truncated)
    buffer.append(postfix)
}

/**
 * Returns the number of elements which match the given *predicate*
 *
 * @includeFunctionBody ../../test/CollectionTest.kt count
 */
public inline fun <T> java.lang.Iterable<T>.count(predicate: (T) -> Boolean) : Int {
    var count = 0
    for (element in this) if (predicate(element)) count++
    return count
}

/**
 * Returns the first element which matches the given *predicate* or *null* if none matched
 *
 * @includeFunctionBody ../../test/CollectionTest.kt find
 */
public inline fun <T> java.lang.Iterable<T>.find(predicate: (T) -> Boolean) : T? {
    for (element in this) if (predicate(element)) return element
    return null
}

/**
 * Filters all elements which match the given predicate into the given list
 *
 * @includeFunctionBody ../../test/CollectionTest.kt filterIntoLinkedList
 */
public inline fun <T, C: Collection<in T>> java.lang.Iterable<T>.filterTo(result: C, predicate: (T) -> Boolean) : C {
    for (element in this) if (predicate(element)) result.add(element)
    return result
}

/**
 * Returns a list containing all elements which do not match the given *predicate*
 *
 * @includeFunctionBody ../../test/CollectionTest.kt filterNotIntoLinkedList
 */
public inline fun <T, L: List<in T>> java.lang.Iterable<T>.filterNotTo(result: L, predicate: (T) -> Boolean) : L {
    for (element in this) if (!predicate(element)) result.add(element)
    return result
}

/**
 * Filters all non-*null* elements into the given list
 *
 * @includeFunctionBody ../../test/CollectionTest.kt filterNotNullIntoLinkedList
 */
public inline fun <T, L: List<in T>> java.lang.Iterable<T?>?.filterNotNullTo(result: L) : L {
    if (this != null) {
        for (element in this) if (element != null) result.add(element)
    }
    return result
}

/**
 * Returns the result of transforming each element to one or more values which are concatenated together into a single list
 *
 * @includeFunctionBody ../../test/CollectionTest.kt flatMap
 */
public inline fun <T, R> java.lang.Iterable<T>.flatMapTo(result: Collection<R>, transform: (T) -> Collection<R>) : Collection<R> {
    for (element in this) {
        val list = transform(element)
        if (list != null) {
            for (r in list) result.add(r)
        }
    }
    return result
}

/**
 * Performs the given *operation* on each element
 *
 * @includeFunctionBody ../../test/CollectionTest.kt forEach
 */
public inline fun <T> java.lang.Iterable<T>.forEach(operation: (T) -> Unit) : Unit = for (element in this) operation(element)

/**
 * Folds all elements from from left to right with the *initial* value to perform the operation on sequential pairs of elements
 *
 * @includeFunctionBody ../../test/CollectionTest.kt fold
 */
public inline fun <T> java.lang.Iterable<T>.fold(initial: T, operation: (T, T) -> T): T {
    var answer = initial
    for (element in this) answer = operation(answer, element)
    return answer
}

/**
 * Folds all elements from right to left with the *initial* value to perform the operation on sequential pairs of elements
 *
 * @includeFunctionBody ../../test/CollectionTest.kt foldRight
 */
public inline fun <T> java.lang.Iterable<T>.foldRight(initial: T, operation: (T, T) -> T): T = reverse().fold(initial, operation)

/**
 * Groups the elements in the collection into a new [[Map]] using the supplied *toKey* function to calculate the key to group the elements by
 *
 * @includeFunctionBody ../../test/CollectionTest.kt groupBy
 */
public inline fun <T, K> java.lang.Iterable<T>.groupBy(toKey: (T) -> K) : Map<K, List<T>> = groupByTo<T,K>(HashMap<K, List<T>>(), toKey)

/**
 * Groups the elements in the collection into the given [[Map]] using the supplied *toKey* function to calculate the key to group the elements by
 *
 * @includeFunctionBody ../../test/CollectionTest.kt groupBy
 */
public inline fun <T, K> java.lang.Iterable<T>.groupByTo(result: Map<K, List<T>>, toKey: (T) -> K) : Map<K, List<T>> {
    for (element in this) {
        val key = toKey(element)
        val list = result.getOrPut(key) { ArrayList<T>() }
        list.add(element)
    }
    return result
}

/**
 * Creates a string from all the elements separated using the *separator* and using the given *prefix* and *postfix* if supplied.
 *
 * If a collection could be huge you can specify a non-negative value of *limit* which will only show a subset of the collection then it will
 * a special *truncated* separator (which defaults to "..."
 *
 * @includeFunctionBody ../../test/CollectionTest.kt makeString
 */
public inline fun <T> java.lang.Iterable<T>.makeString(separator: String = ", ", prefix: String = "", postfix: String = "", limit: Int = -1, truncated: String = "..."): String {
    val buffer = StringBuilder()
    appendString(buffer, separator, prefix, postfix, limit, truncated)
    return buffer.toString().sure()
}

/** Returns a list containing the first elements that satisfy the given *predicate* */
public inline fun <T, L: List<in T>> java.lang.Iterable<T>.takeWhileTo(result: L, predicate: (T) -> Boolean) : L {
    for (element in this) if (predicate(element)) result.add(element) else break
    return result
}

/**
 * Reverses the order the elements into a list
 *
 * @includeFunctionBody ../../test/CollectionTest.kt reverse
 */
public inline fun <T> java.lang.Iterable<T>.reverse() : List<T> {
    val answer = LinkedList<T>()
    for (element in this) answer.addFirst(element)
    return answer
}

/** Copies all elements into the given collection */
public inline fun <in T, C: Collection<in T>> java.lang.Iterable<T>.toCollection(result: C) : C {
    for (element in this) result.add(element)
    return result
}

/** Copies all elements into a [[LinkedList]]  */
public inline fun <in T> java.lang.Iterable<T>.toLinkedList() : LinkedList<T> = toCollection(LinkedList<T>())

/** Copies all elements into a [[List]] */
public inline fun <in T> java.lang.Iterable<T>.toList() : List<T> = toCollection(ArrayList<T>())

/** Copies all elements into a [[List] */
public inline fun <in T> java.lang.Iterable<T>.toCollection() : Collection<T> = toCollection(ArrayList<T>())

/** Copies all elements into a [[Set]] */
public inline fun <in T> java.lang.Iterable<T>.toSet() : Set<T> = toCollection(HashSet<T>())

/** Copies all elements into a [[SortedSet]] */
public inline fun <in T> java.lang.Iterable<T>.toSortedSet() : SortedSet<T> = toCollection(TreeSet<T>())

/**
  TODO figure out necessary variance/generics ninja stuff... :)
public inline fun <in T> java.lang.Iterable<T>.toSortedList(transform: fun(T) : java.lang.Comparable<*>) : List<T> {
    val answer = this.toList()
    answer.sort(transform)
    return answer
}
*/
