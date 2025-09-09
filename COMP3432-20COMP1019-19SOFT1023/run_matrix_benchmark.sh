#!/bin/bash

# ====== Configuration ======
FILE_PATH="matrix_input.txt"
MATRIX_SIZE=1000
THREAD_COUNTS="1 2 4 8 10 20 50 100 200 400 500"
OUTPUT_FILE="matrix_benchmark_results.csv"

# ====== Compile Java files ======
echo "Compiling Java programs..."
mkdir -p build/classes
javac -d build/classes src/opsys_project/MatrixMultiplier_SingleThread.java
javac -d build/classes src/opsys_project/MatrixMultiplier_MultiThread.java

# ====== Create CSV file ======
echo "Threads,ExecutionTime(ms),Mode" > "$OUTPUT_FILE"

# ====== Run Single-threaded version ======
echo "Running single-threaded version..."
echo -e "$FILE_PATH\n$MATRIX_SIZE" | java -cp build/classes opsys_project.MatrixMultiplier_SingleThread > temp_output.txt

TIME=$(grep "Time taken" temp_output.txt | awk '{print $4}')
echo "1,$TIME,Single" >> "$OUTPUT_FILE"

# ====== Run Multi-threaded versions ======
for THREADS in $THREAD_COUNTS; do
    echo "Running multi-threaded version with $THREADS threads..."
    echo -e "$FILE_PATH\n$MATRIX_SIZE\n$THREADS" | java -cp build/classes opsys_project.MatrixMultiplier_MultiThread > temp_output.txt

    TIME=$(grep "Time taken" temp_output.txt | awk '{print $4}')
    echo "$THREADS,$TIME,Multi" >> "$OUTPUT_FILE"
done

rm temp_output.txt
echo "Done. Results saved to $OUTPUT_FILE."