DROP TABLE IF EXISTS public.doctor CASCADE;
DROP TABLE IF EXISTS public.doctor_patient CASCADE;
 DROP TABLE IF EXISTS public.health_care_institution CASCADE;
 DROP TABLE IF EXISTS public.patient CASCADE;


CREATE TABLE IF NOT EXISTS public.health_care_institution
(
    id SERIAL,
    description character varying COLLATE pg_catalog."default" NOT NULL,
    address character varying COLLATE pg_catalog."default" NOT NULL,
    free boolean NOT NULL,
    CONSTRAINT health_care_institution_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.patient
(
    id SERIAL,
    address character varying COLLATE pg_catalog."default" NOT NULL,
    phone_number character varying COLLATE pg_catalog."default" NOT NULL,
    insurance_number integer NOT NULL,
    CONSTRAINT patient_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.doctor
(
    id SERIAL,
    salary integer NOT NULL DEFAULT 0,
    phone_number character varying COLLATE pg_catalog."default" NOT NULL DEFAULT 0,
    description character varying COLLATE pg_catalog."default" NOT NULL DEFAULT 0,
    health_care_institution_id integer,
    CONSTRAINT doctor_pkey PRIMARY KEY (id),
    CONSTRAINT insitution_fk FOREIGN KEY (health_care_institution_id)
        REFERENCES public.health_care_institution (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE IF NOT EXISTS public.doctor_patient
(
    p_id integer NOT NULL,
    d_id integer NOT NULL,
    CONSTRAINT doctor_patient_pkey PRIMARY KEY (p_id, d_id),
    CONSTRAINT doctor_fk FOREIGN KEY (d_id)
        REFERENCES public.doctor (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT patient_fk FOREIGN KEY (p_id)
        REFERENCES public.patient (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);