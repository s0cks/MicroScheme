(begin
    ; IO
    (define newline
        (macro (args) (display "\n")))
    ; Maths
    (define pi 3.14159265359)
    (define tau (* 2 pi))
    (define square (lambda (args) (* args args))))