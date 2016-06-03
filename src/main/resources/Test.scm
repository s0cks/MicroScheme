(begin
    (define red 255)
    (define main-loop
        (lambda (args)
            (display red)
            (if (> red 0)
                (set! red (- red 1))
                (main-loop))
            (main-loop)))
    (main-loop))