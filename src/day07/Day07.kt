package day07

import readInput
import java.math.BigDecimal

fun main() {
    fun part1and2() {
        val input = readInput("Day07", "day07")
        val maxSize = BigDecimal(70000000)
        val updateSizeRequirement = BigDecimal(30000000)
        val fileSystem = FileSystem(input)
        fileSystem.load()
        fileSystem.tree()
        val used = fileSystem.totalSize()
        println("fileSystem.sumOfSmallSizeDirs() " + fileSystem.sumOfSmallSizeDirs())
        println("fileSystem.totalSize() $used")
        val requiredSpaceToFree = updateSizeRequirement.minus(maxSize.minus(used))
        println("requiredSpaceToFree $requiredSpaceToFree")
        val dirToDelete = fileSystem.suggestedDeletion(requiredSpaceToFree)
        if (dirToDelete != null) {
            println("dirToDelete: " + dirToDelete.name() + " " + dirToDelete.totalSize())
        }
    }
    part1and2()
}


class File(input: String) {
    private val fileInfo = input.split(" ")
    fun size(): BigDecimal {
        return BigDecimal(fileInfo[0])
    }

    fun name(): String {
        return fileInfo[1]
    }
}

class Directory(
    private val dirName: String,
    private val files: MutableList<File> = mutableListOf(),
    private val dirs: MutableList<Directory> = mutableListOf()
) {
    private fun filesSize(): BigDecimal {
        var fileSize = BigDecimal(0)
        files.listIterator().forEach {
            fileSize = fileSize.plus(it.size())
        }
        return fileSize
    }

    private fun dirsSizes(): BigDecimal {
        var dirSize = BigDecimal(0)
        dirs.listIterator().forEach {
            dirSize = dirSize.plus(it.totalSize())
        }
        return dirSize
    }

    fun totalSize(): BigDecimal {
        return filesSize().plus(dirsSizes())
    }

    fun name(): String {
        return dirName
    }

    fun addFile(file: File) {
        files.add(file)
    }

    fun printFiles() {
        files.listIterator().forEach {
            println("   └── ${it.name()}")
        }
    }

    fun addDir(dir: Directory) {
        dirs.add(dir)
    }
}

class FileSystem(private val input: List<String>) {
    private var root = Directory("/")
    private var dirMap: MutableMap<String, Directory> = mutableMapOf(root.name() to root)

    fun load() {
        val workingDirectories: MutableList<Directory> = mutableListOf(root)
        var currentDir = root
        val iterator = input.listIterator()
        while (iterator.hasNext()) {
            val it = iterator.next()
            if (it == "$ ls" || it == "$ cd /") continue
            if (it[0] == '$') {
                if (it == "$ cd ..") {
                    workingDirectories.removeAt(workingDirectories.lastIndex)
                    currentDir = workingDirectories[workingDirectories.lastIndex]
                    continue
                }
                if (it.startsWith("$ cd ")) {
                    val dirName = it.split(" ")[2] + "/"
                    val currentDirPath = workingDir(workingDirectories) + dirName
                    workingDirectories.add(dirMap[currentDirPath]!!)
                    currentDir = dirMap[currentDirPath]!!
                    continue
                }
            } else {
                if (it.startsWith("dir ")) {
                    val dirName = it.split(" ")[1] + "/"
                    val newDir = Directory(dirName)
                    val currentDirPath = workingDir(workingDirectories) + dirName
                    dirMap[currentDirPath] = newDir
                    dirMap[currentDirPath]?.let { dir -> currentDir.addDir(dir) }
                    continue
                } else {
                    val file = File(it)
                    currentDir.addFile(file)
                    continue
                }
            }
        }
    }

    private fun workingDir(list: MutableList<Directory>): String {
        var pwd = ""
        list.listIterator().forEach {
            pwd = pwd.plus(it.name())
        }
        return pwd
    }

    fun tree() {
        val dirSortedMap = dirMap.toSortedMap()
        dirSortedMap.iterator().forEach {
            println(it.key)
            it.value.printFiles()
        }
    }

    fun totalSize(): BigDecimal {
        return dirMap["/"]?.totalSize() ?: BigDecimal(0)
    }

    fun sumOfSmallSizeDirs(): BigDecimal {
        var sum = BigDecimal(0)
        dirMap.iterator().forEach {
            val totalSize = it.value.totalSize()
            if (totalSize <= BigDecimal(100000)) {
                sum = sum.plus(totalSize)
            }
        }
        return sum
    }

    fun suggestedDeletion(requiredSpace: BigDecimal): Directory? {
        val iterator = dirMap.iterator()
        var dirToDelete: Directory? = iterator.next().value
        while (iterator.hasNext()) {
            val it = iterator.next()
            if (dirToDelete != null && dirToDelete.totalSize() > it.value.totalSize() && it.value.totalSize() >= requiredSpace) {
                dirToDelete = it.value
                continue
            }
        }
        return dirToDelete
    }
}