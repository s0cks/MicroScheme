(begin
    (define test #t)
    (if (not (eq? test #f))
        (display "Is a test")
        (display "Not a test")))