CREATE TABLE IF NOT EXISTS retail_products."product" (
"product_id" int,
"product_code" text,
"product_description" text,
"product_value" FLOAT,
"currency_code" text,
"insert_date" timestamp,
"inserted_by" text,
"update_date" timestamp,
"updated_by" text,
PRIMARY KEY ("product_id","product_code"));