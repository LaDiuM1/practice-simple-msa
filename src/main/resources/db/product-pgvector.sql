-- 애플리케이션이 study_msa."product" 테이블을 생성한 뒤 실행한다.
-- embedding 컬럼을 pgvector 타입으로 변환하고, 코사인 거리 인덱스를 추가한다.

CREATE EXTENSION IF NOT EXISTS vector;

ALTER TABLE study_msa."product"
    ALTER COLUMN embedding TYPE vector(1536)
    USING embedding::vector(1536);

CREATE INDEX IF NOT EXISTS idx_product_embedding_cosine
    ON study_msa."product"
    USING hnsw (embedding vector_cosine_ops);
