using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using BookingSystem.Models;

namespace BookingSystem.DTOs
{
    public class ServiceDtO
    {
        public string Type { get; set; }
        public double DurrationMinutes { get; set; }

        public ServiceDtO(Service service)
        {
            this.Type = service.Type;
            this.DurrationMinutes = service.DurrationMinutes;
        }

        public Service GetService()
        {
            return new Service
            {
                DurrationMinutes = this.DurrationMinutes,
                Type = this.Type
            };
        }
    }
}
