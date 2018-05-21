INSERT INTO db_cleanser.stages (id, type, subtype, country, language)
VALUES ('1eb073e8-4ee7-11e8-9c2d-fa7ae01bbebc', 'ine', 'san', 'mx', 'es');
INSERT INTO db_cleanser.stages (id, type, subtype, country, language)
VALUES ('508fc7e2-4ee7-11e8-9c2d-fa7ae01bbebc', 'passport', 'san', 'mx', 'es');

INSERT INTO db_cleanser.corrections (id, known_error, word, stage_id)
VALUES ('09047f98-4ee8-11e8-9c2d-fa7ae01bbebc', 'c4sa', 'casa',  '1eb073e8-4ee7-11e8-9c2d-fa7ae01bbebc');
INSERT INTO db_cleanser.corrections (id, known_error, word, stage_id)
VALUES ('0904831c-4ee8-11e8-9c2d-fa7ae01bbebc', 'cas0', 'casa', '1eb073e8-4ee7-11e8-9c2d-fa7ae01bbebc');
INSERT INTO db_cleanser.corrections (id, known_error, word, stage_id)
VALUES ('09048588-4ee8-11e8-9c2d-fa7ae01bbebc', 'cas0', 'caso', '1eb073e8-4ee7-11e8-9c2d-fa7ae01bbebc');
INSERT INTO db_cleanser.corrections (id, known_error, word, stage_id)
VALUES ('74242652-4ee8-11e8-9c2d-fa7ae01bbebc', 'c4so', 'caso', '1eb073e8-4ee7-11e8-9c2d-fa7ae01bbebc');

INSERT INTO db_cleanser.corrections (id, known_error, word, stage_id)
VALUES ('7d88a312-4ee8-11e8-9c2d-fa7ae01bbebc', 'num3ro', 'numero', '508fc7e2-4ee7-11e8-9c2d-fa7ae01bbebc');
INSERT INTO db_cleanser.corrections (id, known_error, word, stage_id)
VALUES ('7d88a59c-4ee8-11e8-9c2d-fa7ae01bbebc', 'numer0', 'numero', '508fc7e2-4ee7-11e8-9c2d-fa7ae01bbebc');
INSERT INTO db_cleanser.corrections (id, known_error, word, stage_id)
VALUES ('7d88a6dc-4ee8-11e8-9c2d-fa7ae01bbebc', 'numer0', 'numera', '508fc7e2-4ee7-11e8-9c2d-fa7ae01bbebc');
INSERT INTO db_cleanser.corrections (id, known_error, word, stage_id)
VALUES ('7d88a81c-4ee8-11e8-9c2d-fa7ae01bbebc', 'num3ra', 'numera', '508fc7e2-4ee7-11e8-9c2d-fa7ae01bbebc');
