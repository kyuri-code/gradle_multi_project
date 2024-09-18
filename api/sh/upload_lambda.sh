#!/bin/bash

# Lambda関数の名前とJARファイルのパスを設定
LAMBDA_FUNCTION_NAME="nakai-sample-lambda"
JAR_FILE_PATH="./build/libs/api-sample.jar"

# アップロード開始時間を記録
START_TIME=$(date +%s)

# Lambda関数にアップロード
aws lambda update-function-code \
    --function-name "$LAMBDA_FUNCTION_NAME" \
    --zip-file "fileb://$JAR_FILE_PATH" \
    --profile mf-env > /dev/null 2>&1

# アップロード終了時間を記録
END_TIME=$(date +%s)

# 経過時間を計算
ELAPSED_TIME=$((END_TIME - START_TIME))

# 結果を表示
echo "Lambda function code upload completed in $ELAPSED_TIME seconds."