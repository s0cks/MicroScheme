(begin
    (define exit (lambda (args) "Done"))
    (define red 255)
    (define main-loop
        (lambda (args)
            (display red)
            (if (> red 0)
                (begin
                    (set! red (- red 1))
                    (main-loop))
                (exit))))
    (main-loop))